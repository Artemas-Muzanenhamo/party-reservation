package com.reservation.confirmation.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class.getName());

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC = "artemas-topic";

    private final ReceiverOptions<String, String> receiverOptions;
    private final SimpleDateFormat dateFormat;

    public KafkaConsumer() {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "confirmation-service-consumer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "confirmation-service-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        receiverOptions = ReceiverOptions.create(props);
        dateFormat = new SimpleDateFormat("HH:mm:ss:SSS z dd MMM yyyy");
    }

    public Flux<ReceiverRecord<String, String>> consumeMessages() {
        ReceiverOptions<String, String> options = receiverOptions.subscription(Collections.singleton(TOPIC))
                .addAssignListener(partitions -> log.debug("onPartitionsAssigned {}", partitions))
                .addRevokeListener(partitions -> log.debug("onPartitionsRevoked {}", partitions));
        return KafkaReceiver.create(options).receive();
    }
}


//    public static void main(String[] args) throws Exception {
//        int count = 20;
//        CountDownLatch latch = new CountDownLatch(count);
//        SampleConsumer consumer = new SampleConsumer(BOOTSTRAP_SERVERS);
//        Disposable disposable = KafkaConsumer.consumeMessages(TOPIC, latch);
//        latch.await(10, TimeUnit.SECONDS);
//        disposable.dispose();
//    }
//}
