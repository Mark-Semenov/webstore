package ru.gb.mark.webstore.config;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class BeanConfig {

    private final FlywayProperties properties;

    @Value("${mail.username}")
    private String mailUser;

    @Value("${mail.pas}")
    private String mailPas;


    private final String url;
    private final String username;
    private final String password;

    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;

    public BeanConfig(FlywayProperties properties,
                      Environment environment,
                      JavaMailSender javaMailSender,
                      MailProperties mailProperties) {
        this.properties = properties;
        this.url = environment.getProperty("dburl");
        this.username = environment.getProperty("dblogin");
        this.password = environment.getProperty("dbpas");
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
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
