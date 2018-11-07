package com.mcrminer.export.perspectives.reviewer;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.model.File;
import com.mcrminer.model.Review;
import com.mcrminer.model.ReviewRequest;
import com.mcrminer.model.User;
import com.mcrminer.repository.FileRepository;
import com.mcrminer.repository.ReviewRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiffReviewerPerspectiveCreationStrategy implements PerspectiveCreationStrategy<User, ReviewerPerspective> {

    private final ReviewRequestRepository reviewRequestRepository;
    private final FileRepository fileRepository;

    @Autowired
    public DiffReviewerPerspectiveCreationStrategy(ReviewRequestRepository reviewRequestRepository, FileRepository fileRepository) {
        this.reviewRequestRepository = reviewRequestRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    @Transactional
    public void fillPerspective(User reviewer, ReviewerPerspective perspective) {
        List<ReviewRequest> reviewRequests = reviewRequestRepository.findAllBySubmitterEmail(reviewer.getEmail());
        reviewRequests.stream().map(ReviewRequest::getDiffs)
                .flatMap(Collection::stream)
                .forEach(diff -> {
                    List<File> diffFiles = fileRepository.findAllByDiffId(diff.getId());
                    long comments = diffFiles.stream().map(File::getComments)
                            .flatMap(Collection::stream)
                            .filter(comment -> comment.getAuthor().equals(reviewer))
                            .count();
                    perspective.setComments(perspective.getComments() + comments);
                    List<Review> authorReviews = diff.getReviews()
                            .stream().filter(review -> review.getAuthor().equals(reviewer))
                            .collect(Collectors.toList());
                    perspective.setReviews((long) authorReviews.size() + perspective.getReviews());
                    long approvals = authorReviews.stream().filter(review -> review.getStatus().isApproval()).count();
                    perspective.setApprovals(perspective.getApprovals() + approvals);
                    long vetos = authorReviews.stream().filter(review -> review.getStatus().isVeto()).count();
                    perspective.setVetos(perspective.getVetos() + vetos);
        });
    }
}
