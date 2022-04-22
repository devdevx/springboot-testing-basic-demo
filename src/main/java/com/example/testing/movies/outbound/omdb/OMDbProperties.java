package com.example.testing.movies.outbound.omdb;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "omdb")
public class OMDbProperties {
    private String apiKey;
    private String baseUrl;
}
