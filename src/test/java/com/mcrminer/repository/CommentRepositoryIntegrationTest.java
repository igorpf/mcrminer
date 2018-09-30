package com.mcrminer.repository;

import com.mcrminer.DatabaseTest;
import com.mcrminer.model.Comment;
import com.mcrminer.model.projections.CommentProjection;
import com.mcrminer.repository.factory.DomainObjectsFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@DatabaseTest
public class CommentRepositoryIntegrationTest {

    @Resource(name = "persistentObjectsDomainFactory")
    private DomainObjectsFactory domainObjectsFactory;
    @Autowired
    private CommentRepository commentRepository;
    private Comment comment1, comment2;
    private List<Comment> allComments;

    @Before
    public void setUp() throws Exception {
        comment1 = domainObjectsFactory.createComment("This looks ok");
        comment2 = domainObjectsFactory.createComment("That seems pretty cool");
        allComments = Arrays.asList(comment1, comment2);
    }

    @Test
    public void testGetProjection() {
        CommentProjection comment = commentRepository.getById(comment1.getId());
        assertThat(comment1.getAuthor().getEmail(), equalTo(comment.getAuthorEmail()));
        assertThat(comment1.getFile().getId(), equalTo(comment.getFileId()));
        assertThat(comment1.getText(), equalTo(comment.getText()));
    }

    @Test
    public void testGetAllProjections() {
        assertThat(commentRepository.getAllBy(), hasSize(allComments.size()));
    }
}