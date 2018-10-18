package com.mcrminer.export.perspectives.reviewer;

import com.mcrminer.export.PerspectiveCreationStrategy;
import com.mcrminer.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewerAssociationsPerspectiveCreationStrategy implements PerspectiveCreationStrategy<User, ReviewerPerspective> {

    @Override
    @Transactional
    public void fillPerspective(User reviewer, ReviewerPerspective perspective) {
        perspective.setEmail(reviewer.getEmail());
        perspective.setFullname(reviewer.getFullname());
        perspective.setUsername(reviewer.getUsername());
    }
}
