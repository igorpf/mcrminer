package com.mcrminer.config;

import com.mcrminer.persistence.model.ApprovalStatus;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Set;

@Profile({"test"})
@Configuration
public class TestConfiguration {

    @Bean("gerritDefaultLabels")
    public Set<ApprovalStatus> getDefaultGerritLabels() {
        return Collections.emptySet();
    }

    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder
                .create()
                .url("jdbc:h2:mem:test")
                .type(HikariDataSource.class).build();
    }
}
