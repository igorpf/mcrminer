package com.mcrminer.persistence.repository;

import com.mcrminer.DatabaseTest;
import com.mcrminer.persistence.model.Project;
import com.mcrminer.persistence.model.ReviewRequest;
import com.mcrminer.persistence.repository.factory.DomainObjectsFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@DatabaseTest
public class ProjectRepositoryIntegrationTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Resource(name = "persistentObjectsDomainFactory")
    private DomainObjectsFactory domainObjectsFactory;

    private Project project;

    @Before
    public void setUp() {
        project = domainObjectsFactory.createProject("https://example.com", "Some project");
        ReviewRequest reviewRequest1 = domainObjectsFactory.createReviewRequest(project);
        ReviewRequest reviewRequest2 = domainObjectsFactory.createReviewRequest(project);
        Set<ReviewRequest> reviewRequests = Stream.of(reviewRequest1, reviewRequest2).collect(Collectors.toSet());
        project.setReviewRequests(reviewRequests);

        projectRepository.save(project);
    }

    @Test
    public void testGetProject() {
        Project savedProject = projectRepository.getOne(project.getId());
        assertThat(savedProject, equalTo(project));
    }
}