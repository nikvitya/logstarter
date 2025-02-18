package ru.t1.java.logstarter.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "http.logging")
@Getter
@Setter
public class HttpLoggingProperties {
    private boolean enable;
    private String level;
}
