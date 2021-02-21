package com.reservation.entry.service.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
public class KafkaTopicConfiguration {
    private static final int NUMBER_OF_PARTITIONS = 1;
    private static final short REPLICATION_FACTOR = (short) 1;
    private final String bootstrapAddress;

    public KafkaTopicConfiguration(@Value("${spring.kafka.producer.bootstrap-servers}") String bootstrapAddress) {
        this.bootstrapAddress = bootstrapAddress;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic artemasTopic() {
        return new NewTopic("artemas-topic", NUMBER_OF_PARTITIONS, REPLICATION_FACTOR);
    }
}
