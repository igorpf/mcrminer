package com.mcrminer.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.mcrminer"})
@EntityScan(basePackages = {"com.mcrminer.persistence"})
@EnableJpaRepositories(basePackages = {"com.mcrminer.persistence"})
public class TestConfiguration {

}
