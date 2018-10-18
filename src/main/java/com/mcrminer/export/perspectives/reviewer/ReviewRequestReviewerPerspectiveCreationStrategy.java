package com.mcrminer.export.perspectives.reviewer;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewRequestReviewerPerspectiveCreationStrategy implements PerspectiveCreationStrategy<User, ReviewerPerspective> {

    @Override
    @Transactional
    public void fillPerspective(User reviewer, ReviewerPerspective perspective) {
        reviewer.getReviewRequests().forEach(reviewRequest -> {
            long comments = reviewRequest.getDiffs().stream()
                    .map(Diff::getFiles).flatMap(Collection::stream).map(File::getComments)
                    .flatMap(Collection::stream)
                    .filter(comment -> comment.getAuthor().equals(reviewer))
                    .count();
            perspective.setComments(comments + perspective.getComments());
            List<Review> authorReviews = reviewRequest.getReviews()
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
