package com.mcrminer.service.export.perspectives;

import com.mcrminer.service.export.DefaultPerspectiveExportConfigurationParameters;
import com.mcrminer.service.export.PerspectiveExportConfigurationParameters;
import com.mcrminer.service.export.impl.MockPerspectiveExportService;
import com.mcrminer.service.export.perspectives.comment.CommentPerspective;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class PerspectiveExportServiceUnitTest {

    private MockPerspectiveExportService perspectiveExportService = new MockPerspectiveExportService();
    private PerspectiveExportConfigurationParameters parameters;
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private String outputString;

    @Before
    public void setUp() throws Exception {
        perspectiveExportService.setOutputStream(outputStream);
        String[] columns = new String[]{"comment"};
        parameters = DefaultPerspectiveExportConfigurationParameters
                .builder()
                .filename("file.txt")
                .columns(columns)
                .perspectiveClass(CommentPerspective.class)
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