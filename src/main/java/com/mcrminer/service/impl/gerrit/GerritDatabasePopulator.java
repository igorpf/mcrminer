package com.mcrminer.service.impl.gerrit;

import com.mcrminer.model.ApprovalStatus;
import com.mcrminer.repository.ApprovalStatusRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Component
public class GerritDatabasePopulator implements ApplicationRunner {

    private static final Set<ApprovalStatus> defaultLabels = new HashSet<>();

    static {
        defaultLabels.add(new ApprovalStatus("-2", "Do not submit", -2, false, true));
        defaultLabels.add(new ApprovalStatus("-1", "I would prefer that you didn't submit this", -1, false, false));
        defaultLabels.add(new ApprovalStatus("0", "No score", 0, false, false));
        defaultLabels.add(new ApprovalStatus("+1", "Looks good to me, but someone else must approve", 1, false, false));
        defaultLabels.add(new ApprovalStatus("+2", "Looks good to me, approved", 2, true, false));
    }

    @Resource
    private ApprovalStatusRepository approvalStatusRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        defaultLabels.forEach(approvalStatus -> approvalStatusRepository.save(approvalStatus));
    }
}
