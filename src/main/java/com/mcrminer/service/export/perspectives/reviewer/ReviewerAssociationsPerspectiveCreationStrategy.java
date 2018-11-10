package com.mcrminer.service.export.perspectives.reviewer;

import com.mcrminer.persistence.model.User;
import com.mcrminer.service.export.PerspectiveCreationStrategy;
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
