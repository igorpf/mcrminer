package com.mcrminer.repository;

import com.mcrminer.model.Project;
import com.mcrminer.model.projections.ProjectProjection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProjectRepositoryIntegrationTest {

    @Autowired
    private ProjectRepository projectRepository;

    private Project project;

    @Before
    public void setUp() {
        project = new Project();
        project.setName("Some project");
        project.setUrlPath("https://example.com");

        projectRepository.save(project);
    }

    @Test
    public void testGetProject() {
        Project savedProject = projectRepository.getOne(project.getId());
        assertThat(savedProject, equalTo(project));
    }

    @Test
    public void testGetProjectProjections() {
        ProjectProjection projection = projectRepository.getById(project.getId());
        assertThat(projection.getName(), equalTo(project.getName()));
        assertThat(projection.getId(), equalTo(project.getId()));
        assertThat(projection.getUrlPath(), equalTo(project.getUrlPath()));
    }
}