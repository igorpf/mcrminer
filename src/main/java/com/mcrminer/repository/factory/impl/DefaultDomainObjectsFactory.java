package com.mcrminer.repository.factory.impl;

import com.mcrminer.model.*;
import com.mcrminer.model.enums.FileStatus;
import com.mcrminer.model.enums.ReviewRequestStatus;
import com.mcrminer.repository.*;
import com.mcrminer.repository.factory.DomainObjectsFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

@Service("persistentObjectsDomainFactory")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultDomainObjectsFactory implements DomainObjectsFactory {

    private ProjectRepository projectRepository;
    private ReviewRequestRepository reviewRequestRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private FileRepository fileRepository;
    private DiffRepository diffRepository;
    private ReviewRepository reviewRepository;
    private ApprovalStatusRepository approvalStatusRepository;

    @Override
    public Project createProject(String urlPath, String name) {
        Project project = new Project();
        project.setUrlPath(urlPath);
        project.setName(name);
        project.setCodeReviewToolId("gerrit");
        return projectRepository.save(project);
    }

    @Override
    public Review createReview(String description, Reviewable reviewed) {
        Review review = new Review();
        review.setReviewed(reviewed);
        review.setAuthor(createUser("review@example.com"));
        review.setDescription(description);
        review.setCreatedTime(LocalDateTime.now().minusWeeks(1));
        review.setUpdatedTime(LocalDateTime.now());
        review.setStatus(createApprovalStatus());
        return reviewRepository.save(review);
    }

    @Override
    public ReviewRequest createReviewRequest(Project project) {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequestRepository.save(reviewRequest);
        reviewRequest.setProject(project);
        reviewRequest.setDiffs(Collections.singleton(createDiff(reviewRequest)));
        reviewRequest.setUpdatedTime(LocalDateTime.now());
        reviewRequest.setCreatedTime(LocalDateTime.now().minusMonths(2));
        reviewRequest.setDescription("new software version");
        reviewRequest.setSubmitter(createUser("user@user.com"));
        reviewRequest.setCommitId("0xdeadbeef");
        reviewRequest.setPublic(true);
        reviewRequest.setStatus(ReviewRequestStatus.MERGED);
        reviewRequest.setBranch("master");
        return reviewRequestRepository.save(reviewRequest);
    }

    @Override
    public Comment createComment(String text, File file) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setAuthor(createUser("user@example.com"));
        comment.setFile(file);
        return commentRepository.save(comment);
    }

    @Override
    public Comment createComment(String text) {
        Project project = createProject("http://default.com", "defaultproject");
        ReviewRequest reviewRequest = createReviewRequest(project);
        Diff diff = createDiff(reviewRequest);
        File file = createFile("somefile", diff);
        return createComment(text, file);
    }

    @Override
    public Diff createDiff(ReviewRequest reviewRequest) {
        Diff diff = new Diff();
        diffRepository.save(diff);
        diff.setReviewRequest(reviewRequest);
        diff.setCreatedTime(LocalDateTime.now().minusDays(2));
        diff.setUpdatedTime(LocalDateTime.now());
        diff.setFiles(Arrays.asList(
                createFile("somefile.txt", diff),
                createFile("anotherfile.java", diff)
        ));
        diff.setReviews(Collections.singleton(
                createReview("cool changes", diff)
        ));
        return diffRepository.save(diff);
    }

    @Override
    public User createUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setFullname("Example user");
        user.setUsername("someusername");
        return userRepository.save(user);
    }

    @Override
    public File createFile(String filename, Diff diff) {
        File file = new File();
        fileRepository.save(file);
        file.setLinesInserted(1L);
        file.setLinesRemoved(2L);
        file.setStatus(FileStatus.MODIFIED);
        file.setNewFilename(filename);
        file.setComments(Arrays.asList(
                createComment("nice feature", file),
                createComment("this is not ok", file)
        ));
        file.setDiff(diff);
        return fileRepository.save(file);
    }

    private ApprovalStatus createApprovalStatus() {
        return approvalStatusRepository.save(new ApprovalStatus("label", "cool", 2, true, false));
    }
}
