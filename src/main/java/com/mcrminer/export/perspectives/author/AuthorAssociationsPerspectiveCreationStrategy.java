package com.mcrminer.export.perspectives.author;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.model.Diff;
import com.mcrminer.model.ReviewRequest;
import com.mcrminer.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class AuthorAssociationsPerspectiveCreationStrategy implements PerspectiveCreationStrategy<User, AuthorPerspective> {

    @Override
    @Transactional
    public void fillPerspective(User author, AuthorPerspective perspective) {
        perspective.setEmail(author.getEmail());
        perspective.setFullname(author.getFullname());
        perspective.setUsername(author.getUsername());
        perspective.setReviewRequests((long) author.getReviewRequests().size());
        perspective.setDiffs(author.getReviewRequests().stream().map(ReviewRequest::getDiffs).mapToLong(Collection::size).sum());
        perspective.setFiles(author.getReviewRequests().stream().map(ReviewRequest::getDiffs).flatMap(Collection::stream).map(Diff::getFiles).mapToLong(Collection::size).sum());
    }
}
