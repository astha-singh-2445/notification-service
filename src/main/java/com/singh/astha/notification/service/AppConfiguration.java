package com.singh.astha.notification.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.singh.astha.notification.service.utils.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Configuration
@EnableWebMvc
public class AppConfiguration {

    private static final List<String> SCOPES = List.of(Constants.MESSAGING_SCOPE);

    @Value("${kafka.topic.name}")
    private String kafkaTopicName;

    @Value("${kafka.topic.partitions}")
    private String kafkaTopicPartitions;

    @Value("${kafka.topic.replicas}")
    private String kafkaTopicReplicas;

    @Bean
    public NewTopic ingestionTopic() {
        return TopicBuilder.name(kafkaTopicName)
                .partitions(Integer.parseInt(kafkaTopicPartitions))
                .replicas(Integer.parseInt(kafkaTopicReplicas))
                .build();
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
