package com.mcrminer.mining.service.impl;

import com.mcrminer.DatabaseTest;
import com.mcrminer.exceptions.ProjectAlreadyImportedException;
import com.mcrminer.mining.service.AuthenticationData;
import com.mcrminer.mining.service.CodeReviewMiningService;
import com.mcrminer.persistence.model.Project;
import com.mcrminer.persistence.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@DatabaseTest
public class AbstractCodeReviewMiningServiceIntegrationTest {

    @Resource(name = "mockCodeReviewMiningService")
    private CodeReviewMiningService codeReviewMiningService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ReviewRequestRepository reviewRequestRepository;
    @Autowired
    private DiffRepository diffRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private CommentRepository commentRepository;

    private final AuthenticationData authData = new AuthenticationData() {
        @Override
        public String getUsername() {
            return "";
        }

        @Override
        public String getPassword() {
            return "";
        }

        @Override
        public String getHost() {
            return "https://localhost";
        }
    };

    @Before
    public void setUp() throws Exception {
    }

    @Test(expected = ProjectAlreadyImportedException.class)
    public void testThatTheSameProjectIsNotImportedTwice() {
        codeReviewMiningService.fetchProject("someProject", authData);
        codeReviewMiningService.fetchProject("someProject", authData);
    }

    @Test
    public void testDeleteProject() {
        Project fetchedProject = codeReviewMiningService.fetchProject("someProject", authData);
        projectRepository.delete(fetchedProject);
        assertThat(projectRepository.findAll(), hasSize(0));
        assertThat(reviewRequestRepository.findAll(), hasSize(0));
        assertThat(diffRepository.findAll(), hasSize(0));
        assertThat(fileRepository.findAll(), hasSize(0));
        assertThat(commentRepository.findAll(), hasSize(0));
    }
}