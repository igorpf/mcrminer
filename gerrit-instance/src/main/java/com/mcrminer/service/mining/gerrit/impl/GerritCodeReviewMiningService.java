package com.mcrminer.service.mining.impl.gerrit.impl;

import com.google.gerrit.extensions.api.GerritApi;
import com.google.gerrit.extensions.client.ListChangesOption;
import com.google.gerrit.extensions.common.ChangeInfo;
import com.google.gerrit.extensions.common.CommentInfo;
import com.google.gerrit.extensions.common.ProjectInfo;
import com.google.gerrit.extensions.restapi.RestApiException;
import com.mcrminer.exceptions.ClientApiException;
import com.mcrminer.persistence.model.Diff;
import com.mcrminer.persistence.model.Project;
import com.mcrminer.persistence.model.Review;
import com.mcrminer.persistence.model.ReviewRequest;
import com.mcrminer.persistence.repository.*;
import com.mcrminer.service.mining.AuthenticationData;
import com.mcrminer.service.mining.impl.AbstractCodeReviewMiningService;
import com.mcrminer.service.mining.impl.gerrit.ApiSupplier;
import com.mcrminer.service.mining.impl.gerrit.GerritApiModelConverter;
import com.urswolfer.gerrit.client.rest.GerritAuthData;
import com.urswolfer.gerrit.client.rest.GerritRestApiFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("gerritCodeReviewMiningService")
public class GerritCodeReviewMiningService extends AbstractCodeReviewMiningService {

    private static final GerritRestApiFactory apiFactory = new GerritRestApiFactory();
    private static final Logger LOG = LoggerFactory.getLogger(GerritCodeReviewMiningService.class);
    private static final String PROJECT_URL_FORMAT = "%s/%s";
    private static final String PROJECT_QUERY = "project:%s";
    private static final int MAX_RETRIES = 20;
    private static final String TOOL_NAME = "Gerrit";
    private static final ListChangesOption[] REVIEW_REQUEST_OPTIONS = {
            ListChangesOption.DETAILED_ACCOUNTS
    };
    private static final ListChangesOption[] REVIEW_OPTIONS = {
            ListChangesOption.DETAILED_LABELS,
            ListChangesOption.DETAILED_ACCOUNTS
    };
    private static final ListChangesOption[] DIFF_OPTIONS = {
            ListChangesOption.ALL_FILES,
            ListChangesOption.ALL_REVISIONS
    };

    private GerritApiModelConverter modelConverter;

    @Autowired
    public GerritCodeReviewMiningService(ProjectRepository projectRepository, ReviewRequestRepository reviewRequestRepository,
                                         DiffRepository diffRepository, CommentRepository commentRepository,
                                         UserRepository userRepository, FileRepository fileRepository,
                                         ReviewRepository reviewRepository, ApprovalStatusRepository approvalStatusRepository,
                                         GerritApiModelConverter modelConverter){
        super(projectRepository, reviewRequestRepository, diffRepository, commentRepository, userRepository, fileRepository, reviewRepository, approvalStatusRepository);
        this.modelConverter = modelConverter;
    }

    @Override
    protected Project getProject(String projectId, AuthenticationData authData) {
        return fetchObjectHandlingException(() -> {
            GerritApi api = getGerritApi(authData);
            ProjectInfo gerritProject = api.projects().name(projectId).get();
            Project project = modelConverter.fromProject(gerritProject);
            project.setUrlPath(String.format(PROJECT_URL_FORMAT, authData.getHost(), projectId));
            return project;
        });
    }

    @Override
    protected List<ReviewRequest> getReviewRequestsForProject(Project project, Pageable pageRequest, AuthenticationData authData) {
        return fetchObjectHandlingException(() -> {
            LOG.info("Fetching review requests for project {}, pageSize {}, page {}", project, pageRequest.getPageSize(), pageRequest.getPageNumber());
            GerritApi api = getGerritApi(authData);
            String projectQuery = String.format(PROJECT_QUERY, project.getCodeReviewToolId());
            List<ChangeInfo> changes = api.changes().query(projectQuery)
                    .withOptions(REVIEW_REQUEST_OPTIONS)
                    .withLimit(pageRequest.getPageSize())
                    .withStart(pageRequest.getPageNumber() * pageRequest.getPageSize())
                    .get();
            return modelConverter.reviewRequestsFromChanges(changes);
        });
    }

    @Override
    protected List<Diff> getDiffsForReviewRequest(ReviewRequest reviewRequest, Pageable pageRequest, AuthenticationData authData) {
        return fetchObjectHandlingException(() -> {
            LOG.info("Fetching diffs for review request {}, pageSize {}, page {}", reviewRequest, pageRequest.getPageSize(), pageRequest.getPageNumber());
            GerritApi api = getGerritApi(authData);
            String projectQuery = String.format(PROJECT_QUERY, reviewRequest.getProject().getCodeReviewToolId());
            List<ChangeInfo> changes = api.changes().query(projectQuery)
                    .withOptions(DIFF_OPTIONS)
                    .withLimit(pageRequest.getPageSize())
                    .withStart(pageRequest.getPageNumber() * pageRequest.getPageSize())
                    .get();
            Map<ChangeInfo, Map<String, List<CommentInfo>>> changeInfoCommentsMap = new HashMap<>();
            for (ChangeInfo change : changes) {
                changeInfoCommentsMap.put(
                        change,
                        fetchObjectHandlingException( () -> api.changes().id(change.id).comments())
                );
            }
            return modelConverter.diffsFromChanges(changes,changeInfoCommentsMap );
        });
    }

    @Override
    protected List<Review> getReviewsForReviewRequest(ReviewRequest reviewRequest, Pageable pageRequest, AuthenticationData authData) {
        return fetchObjectHandlingException(() -> {
            LOG.info("Fetching reviews for review request {}, pageSize {}, page {}", reviewRequest, pageRequest.getPageSize(), pageRequest.getPageNumber());
            GerritApi api = getGerritApi(authData);
            String projectQuery = String.format(PROJECT_QUERY, reviewRequest.getProject().getCodeReviewToolId());
            return modelConverter.reviewsFromChanges(api.changes().query(projectQuery)
                    .withOptions(REVIEW_OPTIONS)
                    .withLimit(pageRequest.getPageSize())
                    .withStart(pageRequest.getPageNumber() * pageRequest.getPageSize())
                    .get());
        });
    }

    private <T> T fetchObjectHandlingException(ApiSupplier<T> supplier) {
        int retryCounter = MAX_RETRIES;
        while (retryCounter-- > 0) {
            try {
                return supplier.get();
            } catch (RestApiException restApiException) {
                LOG.error("Error connecting to Gerrit REST API, retries left: {}", retryCounter);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Exception details", restApiException);
                }
            }
        }
        throw new ClientApiException("Error connecting to Gerrit REST API");
    }

    private GerritApi getGerritApi(AuthenticationData authData) {
        return apiFactory.create(getGerritAuth(authData));
    }

    private GerritAuthData getGerritAuth(AuthenticationData authData) {
        return new GerritAuthData.Basic(authData.getHost(), authData.getUsername(), authData.getPassword());
    }

    @Override
    public String getToolName() {
        return TOOL_NAME;
    }
}
