package com.mcrminer.service;

import com.mcrminer.model.enums.ReviewRequestStatus;
import com.mcrminer.model.projections.ReviewRequestProjection;
import com.mcrminer.service.impl.DefaultExportService;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExportServiceTest {
    private static final String FIELD_SEPARATOR = ",";
    private static final String LINE_FEED = "\n";
    private ExportService exportService = new DefaultExportService();
    private ReviewRequestProjection reviewRequest1, reviewRequest2;
    private String csvFileContent;

    @Before
    public void setUp() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        reviewRequest1 = ReviewRequestProjection
                .builder()
                .branch("master").commitId("deadbeef").description("Some cool feature")
                .projectId(1).submitterEmail("example@domain.com").status(ReviewRequestStatus.MERGED)
                .createdTime(now).updatedTime(now)
                .build();
        reviewRequest2 = ReviewRequestProjection
                .builder().isPublic(true)
                .branch("develop").commitId("deadbeef").description("Bug fixing")
                .projectId(2).submitterEmail("user@domain.com").status(ReviewRequestStatus.PENDING)
                .createdTime(now).updatedTime(now)
                .build();
        csvFileContent = new StringBuffer("projectId,branch,commitId,description,submitterEmail,isPublic,status,createdTime,updatedTime")
                .append(LINE_FEED)
                .append(getCsvLineFor(reviewRequest1))
                .append(getCsvLineFor(reviewRequest2))
                .toString();
    }

    @Test
    public void testReviewRequestExport() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream);

        List<ReviewRequestProjection> reviews = Arrays.asList(reviewRequest1, reviewRequest2);
        exportService.exportAsCsv(printWriter, reviews, ReviewRequestProjection.class);
        assertThat(outputStream.toString(), equalTo(csvFileContent));
    }

    private String getCsvLineFor(ReviewRequestProjection reviewRequest) {
        return new StringBuffer()
                .append(reviewRequest.getProjectId()).append(FIELD_SEPARATOR)
                .append(reviewRequest.getBranch()).append(FIELD_SEPARATOR)
                .append(reviewRequest.getCommitId()).append(FIELD_SEPARATOR)
                .append(reviewRequest.getDescription()).append(FIELD_SEPARATOR)
                .append(reviewRequest.getSubmitterEmail()).append(FIELD_SEPARATOR)
                .append(reviewRequest.getIsPublic()).append(FIELD_SEPARATOR)
                .append(reviewRequest.getStatus()).append(FIELD_SEPARATOR)
                .append(reviewRequest.getCreatedTime()).append(FIELD_SEPARATOR)
                .append(reviewRequest.getUpdatedTime())
                .append(LINE_FEED)
                .toString();
    }
}