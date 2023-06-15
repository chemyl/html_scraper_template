package org.example.config;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Log4j
@ConfigurationProperties
public class Routes {

    @Autowired
    private Environment environment;

    public String getActivityURL() {
        log.debug("Routes: url created");
        return environment.getProperty("url.base") + environment.getProperty("key.word");
    }
}
