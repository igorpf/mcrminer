package com.mcrminer.service.impl.gerrit;

import com.mcrminer.model.ApprovalStatus;
import com.mcrminer.repository.ApprovalStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@Profile({"!test"})
public class GerritDatabasePopulator implements ApplicationRunner {

    private static final Set<ApprovalStatus> defaultLabels;
    static {
        Set<ApprovalStatus> labels = new HashSet<>();
        labels.add(new ApprovalStatus("-2", "Do not submit", -2, false, true));
        labels.add(new ApprovalStatus("-1", "I would prefer that you didn't submit this", -1, false, false));
        labels.add(new ApprovalStatus("0", "No score", 0, false, false));
        labels.add(new ApprovalStatus("+1", "Looks good to me, but someone else must approve", 1, false, false));
        labels.add(new ApprovalStatus("+2", "Looks good to me, approved", 2, true, false));
        defaultLabels = Collections.unmodifiableSet(labels);
    }

    @Bean("gerritDefaultLabels")
    public Set<ApprovalStatus> getDefaultGerritLabels() {
        return defaultLabels;
    }

    private ApprovalStatusRepository approvalStatusRepository;

    @Autowired
    public GerritDatabasePopulator(ApprovalStatusRepository approvalStatusRepository) {
        this.approvalStatusRepository = approvalStatusRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        defaultLabels.forEach(approvalStatus -> approvalStatusRepository.save(approvalStatus));
    }
}
