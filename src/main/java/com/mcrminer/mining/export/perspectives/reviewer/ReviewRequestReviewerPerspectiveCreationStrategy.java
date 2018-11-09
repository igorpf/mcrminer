package com.mcrminer.mining.export.perspectives.reviewer;

import com.mcrminer.mining.export.PerspectiveCreationStrategy;
import com.mcrminer.persistence.model.Diff;
import com.mcrminer.persistence.model.File;
import com.mcrminer.persistence.model.Review;
import com.mcrminer.persistence.model.User;
import com.mcrminer.persistence.repository.DiffRepository;
import com.mcrminer.persistence.repository.FileRepository;
import com.mcrminer.persistence.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewRequestReviewerPerspectiveCreationStrategy implements PerspectiveCreationStrategy<User, ReviewerPerspective> {

    private final DiffRepository diffRepository;
    private final FileRepository fileRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewRequestReviewerPerspectiveCreationStrategy(DiffRepository diffRepository,
                                                            FileRepository fileRepository,
                                                            ReviewRepository reviewRepository) {
        this.diffRepository = diffRepository;
        this.fileRepository = fileRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public void fillPerspective(User reviewer, ReviewerPerspective perspective) {
        reviewer.getReviewRequests().forEach(reviewRequest -> {
            List<Diff> diffs = diffRepository.findAllByReviewRequestId(reviewRequest.getId());
            long comments = diffs.stream()
                    .map(diff -> fileRepository.findAllByDiffId(diff.getId()))
                    .flatMap(Collection::stream)
                    .map(File::getComments)
                    .flatMap(Collection::stream)
                    .filter(comment -> comment.getAuthor().equals(reviewer))
                    .count();
            perspective.setComments(comments + perspective.getComments());
            List<Review> authorReviews = reviewRepository.findAllByReviewedId(reviewRequest.getId())
                    .stream().filter(review -> review.getAuthor().equals(reviewer))
                    .collect(Collectors.toList());
            perspective.setReviews((long) authorReviews.size() + perspective.getReviews());
            long approvals = authorReviews.stream().filter(review -> review.getStatus().isApproval()).count();
            perspective.setApprovals(approvals + perspective.getApprovals());
            long vetos = authorReviews.stream().filter(review -> review.getStatus().isVeto()).count();
            perspective.setVetos(perspective.getVetos() + vetos);
        });
    }
}
