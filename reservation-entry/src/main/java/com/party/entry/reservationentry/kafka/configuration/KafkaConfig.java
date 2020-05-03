package com.party.entry.reservationentry.kafka.configuration;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfig {
    private String server;
    private String key;
    private String value;

    public KafkaConfig(
            @Value("${party-reservation.kafka.servers}") String server,
            @Value("${party-reservation.kafka.serializers.key}") String key,
            @Value("${party-reservation.kafka.serializers.value}") String value) {
        this.server = server;
        this.key = key;
        this.value = value;
    }

    @Bean
    public Producer<String, String> producer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", server);
        properties.put("key.serializer", key);
        properties.put("value.serializer", value);

        return new KafkaProducer<>(properties);
    }
}
