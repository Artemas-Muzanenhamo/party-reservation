package com.reservation.confirmation.configuration;

import com.reservation.message.ReservationMessageJson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class ReactiveKafkaConfiguration {
    private static final String TOPIC = "artemas-topic";
    private final KafkaProperties kafkaProperties;

    public ReactiveKafkaConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getConsumer().getBootstrapServers());
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaProperties.getConsumer().getClientId());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getKeyDeserializer());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getConsumer().getAutoOffsetReset());

        return properties;
    }

    @Bean
    public ReceiverOptions<String, ReservationMessageJson> receiverOptions() {
        ReceiverOptions<String, ReservationMessageJson> receiverOptions = ReceiverOptions.create();
        JsonDeserializer<ReservationMessageJson> valueDeserializer = new JsonDeserializer<>(ReservationMessageJson.class);
        receiverOptions.withValueDeserializer(valueDeserializer);
        receiverOptions.consumerProperties().putAll(consumerConfigs());
        receiverOptions.subscription(Set.of(TOPIC));
        return receiverOptions;
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, ReservationMessageJson> kafkaConsumerTemplate() {
        return new ReactiveKafkaConsumerTemplate<>(receiverOptions());
    }
}
