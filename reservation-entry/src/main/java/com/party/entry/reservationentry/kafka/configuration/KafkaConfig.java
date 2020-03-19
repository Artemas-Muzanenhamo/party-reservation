package com.party.entry.reservationentry.kafka.configuration;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfig {

    @Bean
    public Producer<String, String> producer() {
        Properties properties = new Properties();

        return new KafkaProducer<>(properties);
    }
}
