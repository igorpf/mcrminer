package com.mcrminer.persistence.repository;

import com.mcrminer.DatabaseTest;
import com.mcrminer.config.TestConfiguration;
import com.mcrminer.persistence.model.ApprovalStatus;
import com.mcrminer.persistence.model.projections.ApprovalStatusProjection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = {TestConfiguration.class})
@RunWith(SpringRunner.class)
@DatabaseTest
public class ApprovalStatusRepositoryIntegrationTest {

    @Autowired
    private ApprovalStatusRepository approvalStatusRepository;
    private ApprovalStatus approvalStatus1, approvalStatus2, approvalStatus3;
    private List<ApprovalStatus> allApprovals;

    @Before
    public void setUp() throws Exception {
        approvalStatus1 = new ApprovalStatus("+2", "I liked it", 2, true, false);
        approvalStatus2 = new ApprovalStatus("+1", "Nice", 1, false, false);
        approvalStatus3 = new ApprovalStatus("0", "Neutral", 0, false, false);
        allApprovals = Arrays.asList(approvalStatus1, approvalStatus2, approvalStatus3);
        approvalStatusRepository.saveAll(allApprovals);
    }

    @Test
    public void testGetProjection() {
        ApprovalStatusProjection projection = approvalStatusRepository.getByLabel(approvalStatus1.getLabel());
        assertThat(projection.getLabel(), equalTo(approvalStatus1.getLabel()));
        assertThat(projection.getDescription(), equalTo(approvalStatus1.getDescription()));
        assertThat(projection.getIsApproval(), equalTo(approvalStatus1.isApproval()));
        assertThat(projection.getIsVeto(), equalTo(approvalStatus1.isVeto()));
    }

    @Test
    public void testGetAllApprovals() {
        assertThat(approvalStatusRepository.getAllBy(), hasSize(allApprovals.size()));
    }
}