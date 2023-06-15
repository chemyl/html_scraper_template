package org.example;

import org.example.config.Routes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(Routes.class)
public class ScraperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScraperApplication.class);
    }
}
