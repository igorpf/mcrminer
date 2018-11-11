package com.mcrminer.persistence.repository;

import com.mcrminer.DatabaseTest;
import com.mcrminer.config.TestConfiguration;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(classes = {TestConfiguration.class})
@RunWith(SpringRunner.class)
@DatabaseTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ReviewRequestRepository reviewRequestRepository;

    @Resource(name = "persistentObjectsDomainFactory")
    private DomainObjectsFactory domainObjectsFactory;

    private Project project;

    @Before
    public void setUp() throws Exception {
        project = domainObjectsFactory.createProject("https://example.com", "Some project");
        ReviewRequest reviewRequest1 = domainObjectsFactory.createReviewRequest(project);
        Set<ReviewRequest> reviewRequests = Stream.of(reviewRequest1).collect(Collectors.toSet());
        project.setReviewRequests(reviewRequests);
        projectRepository.save(project);
    }

    @Test
    public void testGetAllUsersByProject() {
        assertThat(userRepository.findAllByReviewRequestsProjectId(project.getId()), hasSize(1));
    }
}