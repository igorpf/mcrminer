package com.mcrminer.service.impl;

import com.mcrminer.DatabaseTest;
import com.mcrminer.model.Project;
import com.mcrminer.model.projections.ProjectProjection;
import com.mcrminer.repository.ProjectRepository;
import com.mcrminer.service.AuthenticationData;
import com.mcrminer.service.CodeReviewMiningService;
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
    private final AuthenticationData authData = new AuthenticationData() {
        @Override
        public String getUsername() {
            return "";
        }

        @Override
        public String getPassword() {
            return "";
        }
    };

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        Project fetchedProject = codeReviewMiningService.fetchProject("https://localhost", "someProject", authData);
        ProjectProjection savedProject = projectRepository.getById(fetchedProject.getId());
        assertThat(savedProject.getReviewRequests(), hasSize(2));
    }
}