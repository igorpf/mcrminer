package com.mcrminer.export.perspectives.comment;

import com.mcrminer.DatabaseTest;
import com.mcrminer.model.Comment;
import com.mcrminer.repository.factory.DomainObjectsFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@DatabaseTest
public class CommentPerspectiveServiceIntegrationTest {

    @Autowired
    private DomainObjectsFactory domainObjectsFactory;
    @Autowired
    private CommentPerspectiveService commentPerspectiveService;
    private Comment comment;

    @Before
    public void setUp() throws Exception {
        comment = domainObjectsFactory.createComment("cool");
    }

    @Test
    public void testCreateCommentPerpsective() {
        CommentPerspective commentPerspective = commentPerspectiveService.createPerspective(comment);
        assertNotNull(commentPerspective);
    }
}