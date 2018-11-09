package com.mcrminer.mining.export.perspectives.comment;

import com.mcrminer.persistence.model.Comment;
import com.mcrminer.persistence.model.File;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CommentPerspectiveServiceUnitTest {

    private MockCommentPerspectiveService commentPerspectiveService;
    private MockCommentPerspectiveCreationStrategy strategy = new MockCommentPerspectiveCreationStrategy();
    private Comment comment;
    private File file;

    @Before
    public void setUp() throws Exception {
        commentPerspectiveService = new MockCommentPerspectiveService(Collections.singletonList(strategy));
        file = File.builder().newFilename("newfile.txt").build();
        comment = Comment.builder().id(1L).text("some comment").file(file).build();
    }

    @Test
    public void testCommentPerspective() {
        CommentPerspective perspective = commentPerspectiveService.createPerspective(comment);
        assertThat(perspective.getComment(), equalTo(comment.getText()));
        assertThat(perspective.getId(), equalTo(comment.getId()));
        assertThat(perspective.getFilename(), equalTo(comment.getFile().getNewFilename()));
    }
}