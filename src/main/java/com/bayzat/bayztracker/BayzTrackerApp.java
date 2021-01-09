package com.bayzat.bayztracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.bayzat.bayztracker.repository")
@EnableTransactionManagement
public class BayzTrackerApp implements WebMvcConfigurer {
    @Autowired
    AbstractApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(BayzTrackerApp.class, args);
    }
}

