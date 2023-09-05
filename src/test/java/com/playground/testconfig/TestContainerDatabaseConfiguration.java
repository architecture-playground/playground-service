package com.playground.testconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.time.Duration;

import static org.testcontainers.containers.PostgreSQLContainer.DEFAULT_TAG;

@Configuration
public class TestContainerDatabaseConfiguration {

    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "system";

    private static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres").withTag(DEFAULT_TAG))
            .withUsername(USERNAME)
            .withPassword(PASSWORD)
            .withEnv("POSTGRESQL_USER", USERNAME)
            .withEnv("POSTGRESQL_PASSWORD", PASSWORD)
            .withExposedPorts(PostgreSQLContainer.POSTGRESQL_PORT);

    static {
        CONTAINER.setWaitStrategy(Wait.forListeningPort()
                .withStartupTimeout(Duration.ofSeconds(30)));

        CONTAINER.start();
    }

    @Bean
    @ConditionalOnBean(name = "postgreSQLContainer")
    public DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(postgreSQLContainer.getJdbcUrl());
        dataSource.setUsername(postgreSQLContainer.getUsername());
        dataSource.setPassword(postgreSQLContainer.getPassword());
        return dataSource;
    }
}
