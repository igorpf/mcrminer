package com.mcrminer.service.impl.gerrit.impl;

import com.google.gerrit.extensions.api.GerritApi;
import com.google.gerrit.extensions.client.ListChangesOption;
import com.google.gerrit.extensions.common.ProjectInfo;
import com.google.gerrit.extensions.restapi.RestApiException;
import com.mcrminer.exceptions.ClientApiException;
import com.mcrminer.model.Diff;
import com.mcrminer.model.Project;
import com.mcrminer.model.Review;
import com.mcrminer.model.ReviewRequest;
import com.mcrminer.service.AuthenticationData;
import com.mcrminer.service.impl.AbstractCodeReviewMiningService;
import com.mcrminer.service.impl.gerrit.ApiSupplier;
import com.mcrminer.service.impl.gerrit.GerritApiModelConverter;
import com.urswolfer.gerrit.client.rest.GerritAuthData;
import com.urswolfer.gerrit.client.rest.GerritRestApiFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("gerritCodeReviewMiningService")
public class GerritCodeReviewMiningService extends AbstractCodeReviewMiningService {

    private static final GerritRestApiFactory apiFactory = new GerritRestApiFactory();
    private static final Logger LOG = LoggerFactory.getLogger(GerritCodeReviewMiningService.class);
    private static final String PROJECT_URL_FORMAT = "%s/%s";
    private static final String PROJECT_QUERY = "project:%s";
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
    private static final int QUERY_LIMIT = 1;
    @Autowired
    private GerritApiModelConverter modelConverter;

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
    protected List<ReviewRequest> getReviewRequestsForProject(Project project, AuthenticationData authData) {
        return fetchObjectHandlingException(() -> {
            GerritApi api = getGerritApi(authData);
            String projectQuery = String.format(PROJECT_QUERY, project.getCodeReviewToolId());
            return modelConverter.reviewRequestsFromChanges(api.changes().query(projectQuery)
                    .withOptions(REVIEW_REQUEST_OPTIONS)
                    .withLimit(QUERY_LIMIT)
                    .get());
        });
    }

    @Override
    protected List<Diff> getDiffsForReviewRequest(ReviewRequest reviewRequest, AuthenticationData authData) {
        return fetchObjectHandlingException(() -> {
            GerritApi api = getGerritApi(authData);
            String projectQuery = String.format(PROJECT_QUERY, reviewRequest.getProject().getCodeReviewToolId());
            return modelConverter.diffsFromChanges(api.changes().query(projectQuery)
                    .withOptions(DIFF_OPTIONS)
                    .withLimit(QUERY_LIMIT)
                    .get());
        });
    }

    @Override
    protected List<Review> getReviewsForReviewRequest(ReviewRequest reviewRequest, AuthenticationData authData) {
        return fetchObjectHandlingException(() -> {
            GerritApi api = getGerritApi(authData);
            String projectQuery = String.format(PROJECT_QUERY, reviewRequest.getProject().getCodeReviewToolId());
            return modelConverter.reviewsFromChanges(api.changes().query(projectQuery)
                    .withOptions(REVIEW_OPTIONS)
                    .withLimit(QUERY_LIMIT)
                    .get());
        });
    }

    private <T> T fetchObjectHandlingException(ApiSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (RestApiException restApiException) {
            LOG.error("Error connecting to Gerrit API", restApiException);
            throw new ClientApiException(restApiException);
        }
    }

    private GerritApi getGerritApi(AuthenticationData authData) {
        return apiFactory.create(getGerritAuth(authData));
    }

    private GerritAuthData getGerritAuth(AuthenticationData authData) {
        return new GerritAuthData.Basic(authData.getHost(), authData.getUsername(), authData.getPassword());
    }
}
