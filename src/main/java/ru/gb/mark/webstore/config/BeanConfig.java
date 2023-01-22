package ru.gb.mark.webstore.config;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class BeanConfig {

    private final FlywayProperties properties;

    private final String url;
    private final String username;
    private final String password;

    public BeanConfig(FlywayProperties properties, Environment env) {
        this.properties = properties;
        this.url = env.getProperty("dburl");
        this.username = env.getProperty("dblogin");
        this.password = env.getProperty("dbpas");
    }


    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpServletRequest request(HttpServletRequest request) {
        return request;
    }

    @Bean
    public DataSource dbConfig() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        return dataSourceBuilder
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @PostConstruct
    private void flywayProperties() {
        properties.setUrl(url);
        properties.setUser(username);
        properties.setPassword(password);
    }


}
