package com.mcrminer.export.perspectives.reviewer;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.model.Diff;
import com.mcrminer.model.File;
import com.mcrminer.model.Review;
import com.mcrminer.model.User;
import com.mcrminer.repository.DiffRepository;
import com.mcrminer.repository.FileRepository;
import com.mcrminer.repository.ReviewRepository;
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
