package com.mcrminer.repository.factory.impl;

import com.mcrminer.model.*;
import com.mcrminer.model.enums.FileStatus;
import com.mcrminer.repository.*;
import com.mcrminer.repository.factory.DomainObjectsFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("persistentObjectsDomainFactory")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultDomainObjectsFactory implements DomainObjectsFactory {

    private ProjectRepository projectRepository;
    private ReviewRequestRepository reviewRequestRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private FileRepository fileRepository;
    private final String DEFAULT_EMAIL = "user@example.com";
    private final String DEFAULT_FILENAME = "file.txt";
    private final String DEFAULT_FULLNAME = "Example user";
    private final String DEFAULT_USERNAME = "someusername";

    @Override
    public Project createProject(String urlPath, String name) {
        Project newProject = new Project();
        newProject.setUrlPath(urlPath);
        newProject.setName(name);
        return projectRepository.save(newProject);
    }

    @Override
    public ReviewRequest createReviewRequest(Project project) {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setProject(project);
        return reviewRequestRepository.save(reviewRequest);
    }

    @Override
    public Comment createComment(String text) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setAuthor(createUser(DEFAULT_EMAIL));
        comment.setFile(createFile(DEFAULT_FILENAME));
        return commentRepository.save(comment);
    }

    @Override
    public User createUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setFullname(DEFAULT_FULLNAME);
        user.setUsername(DEFAULT_USERNAME);
        return userRepository.save(user);
    }

    @Override
    public File createFile(String filename) {
        File file = new File();
        file.setLinesInserted(1L);
        file.setLinesRemoved(2L);
        file.setStatus(FileStatus.MODIFIED);
        file.setNewFilename(filename);
        return fileRepository.save(file);
    }


}
