package com.reservation.confirmation.configuration;

import com.reservation.message.ReservationMessageJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Set;

@Configuration
public class ReactiveKafkaConfiguration {
    private final KafkaProperties kafkaProperties;
    @Value("${topic.name.consumer}")
    private String topic;

    public ReactiveKafkaConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ReceiverOptions<String, ReservationMessageJson> receiverOptions() {
        ReceiverOptions<String, ReservationMessageJson> receiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        return receiverOptions.subscription(Set.of(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, ReservationMessageJson> kafkaConsumerTemplate() {
        return new ReactiveKafkaConsumerTemplate<>(receiverOptions());
    }
}
