package com.mcrminer.service.export.perspectives.author;

import com.mcrminer.persistence.model.Diff;
import com.mcrminer.persistence.model.ReviewRequest;
import com.mcrminer.persistence.model.User;
import com.mcrminer.service.export.PerspectiveCreationStrategy;
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
