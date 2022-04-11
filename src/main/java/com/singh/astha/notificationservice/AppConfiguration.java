package com.singh.astha.notificationservice;

import com.google.auth.oauth2.GoogleCredentials;
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
import java.util.Arrays;

@Configuration
public class AppConfiguration {

    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = {MESSAGING_SCOPE};

    @Bean
    public NewTopic ingestionTopic() {
        return TopicBuilder.name("notification_ingestion").partitions(8).replicas(1).build();
    }

    @Bean
    public WebClient getWebClient() {
        ConnectionProvider provider = ConnectionProvider.builder("fixed")
                .maxConnections(500)
                .maxIdleTime(Duration.ofSeconds(20))
                .maxLifeTime(Duration.ofSeconds(60))
                .pendingAcquireTimeout(Duration.ofSeconds(60))
                .evictInBackground(Duration.ofSeconds(120)).build();

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create(provider)))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public GoogleCredentials getGoogleCredential() throws IOException {
        return GoogleCredentials
                .fromStream(new FileInputStream("medicine-b627f-firebase-adminsdk-d468k-d62bff6970.json"))
                .createScoped(Arrays.asList(SCOPES));
//        googleCredentials.refresh();
//        return googleCredentials.getAccessToken().getTokenValue();
    }

}
