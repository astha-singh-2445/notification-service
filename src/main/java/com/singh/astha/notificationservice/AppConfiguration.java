package com.singh.astha.notificationservice;

import com.google.auth.oauth2.GoogleCredentials;
import com.singh.astha.notificationservice.utils.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Configuration
public class AppConfiguration {

    private static final List<String> SCOPES = List.of(Constants.MESSAGING_SCOPE);

    @Bean
    public NewTopic ingestionTopic() {
        return TopicBuilder.name(Constants.NOTIFICATION_INGESTION).partitions(8).replicas(1).build();
    }

    @Bean
    public WebClient getWebClient() {
        ConnectionProvider provider = ConnectionProvider.builder(Constants.FIXED)
                .maxConnections(500)
                .maxIdleTime(Duration.ofSeconds(20))
                .maxLifeTime(Duration.ofSeconds(60))
                .pendingAcquireTimeout(Duration.ofSeconds(60))
                .evictInBackground(Duration.ofSeconds(120))
                .build();

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create(provider)))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public GoogleCredentials getGoogleCredential() throws IOException {
        return GoogleCredentials
                .fromStream(new FileInputStream(Constants.FIREBASE_PRIVATE_KEY_JSON))
                .createScoped(SCOPES);
    }

}
