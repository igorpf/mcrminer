package com.mcrminer.config;

import com.mcrminer.model.ApprovalStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collections;
import java.util.Set;

@Profile({"test"})
@Configuration
public class TestConfiguration {

    @Bean("gerritDefaultLabels")
    public Set<ApprovalStatus> getDefaultGerritLabels() {
        return Collections.emptySet();
    }
}
