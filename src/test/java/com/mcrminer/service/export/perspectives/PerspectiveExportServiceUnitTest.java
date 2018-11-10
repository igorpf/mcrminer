package com.mcrminer.service.export.perspectives;

import com.mcrminer.service.export.DefaultPerspectiveExportConfigurationParameters;
import com.mcrminer.service.export.PerspectiveExportConfigurationParameters;
import com.mcrminer.service.export.PerspectiveService;
import com.mcrminer.service.export.PerspectiveType;
import com.mcrminer.service.export.impl.MockPerspectiveExportService;
import com.mcrminer.service.export.perspectives.comment.CommentAssociationsPerspectiveCreationStrategy;
import com.mcrminer.service.export.perspectives.comment.CommentPerspective;
import com.mcrminer.service.export.perspectives.comment.MockCommentPerspectiveService;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class PerspectiveExportServiceUnitTest {

    private MockPerspectiveExportService perspectiveExportService = new MockPerspectiveExportService();
    private PerspectiveExportConfigurationParameters parameters;
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private String outputString;
    private MockCommentPerspectiveService mockCommentPerspectiveService = new  MockCommentPerspectiveService(
            Collections.singletonList(new CommentAssociationsPerspectiveCreationStrategy())
    );
    private PerspectiveType perspectiveType = new PerspectiveType() {
        @Override
        public String getTitleLocalizationKey() {
            return null;
        }

        @Override
        public Class<?> getPerspectiveClass() {
            return CommentPerspective.class;
        }

        @Override
        public PerspectiveService<?, ?> getPerspectiveService() {
            return mockCommentPerspectiveService;
        }
    };

    @Before
    public void setUp() throws Exception {
        perspectiveExportService.setOutputStream(outputStream);
        String[] columns = new String[]{"comment"};
        parameters = DefaultPerspectiveExportConfigurationParameters
                .builder()
                .filename("file.txt")
                .columns(columns)
                .perspectiveClass(CommentPerspective.class)
                .perspectiveType(perspectiveType)
                .lineEnd("\n")
                .build();
        outputString = "comment%shello%shi%ssome comment%stest comment%s".replace("%s", parameters.getLineEnd());
    }

    @Test
    public void testPerspectiveExport() {
        perspectiveExportService.exportPerspective(parameters);
        assertThat(outputStream.toString(), equalTo(outputString));
    }
}