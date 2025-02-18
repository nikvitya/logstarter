package ru.t1.java.logstarter.autoconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.t1.java.logstarter.aop.HttpLoggingAspect;
import ru.t1.java.logstarter.configuration.HttpLoggingProperties;

@Configuration
@EnableConfigurationProperties(HttpLoggingProperties.class)
@RequiredArgsConstructor
public class HttpLoggingAutoConfiguration {
    private final HttpLoggingProperties properties;

    @Bean
    @ConditionalOnProperty(name = "http.logging.enabled", havingValue = "true", matchIfMissing = false)
    public HttpLoggingAspect createLogger() {
        return new HttpLoggingAspect(properties);
    }
}
