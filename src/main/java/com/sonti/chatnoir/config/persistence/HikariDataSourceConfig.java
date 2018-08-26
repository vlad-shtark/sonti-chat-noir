package com.sonti.chatnoir.config.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class HikariDataSourceConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        log.info("DataSource: dbUrl = '{}'", dbUrl);
        log.info("DataSource: username = '{}'", username);
        log.info("DataSource: password = '{}'", password);
        if (dbUrl == null || dbUrl.isEmpty()) {
            return new HikariDataSource();
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            if(username != null && !username.isEmpty()) {
                config.setUsername(username);
            }
            if(password != null && !password.isEmpty()) {
                config.setPassword(password);
            }
            return new HikariDataSource(config);
        }
    }

}
