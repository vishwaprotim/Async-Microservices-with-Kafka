package com.protim.service.credit.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CreditReportListener {

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void listen(ConsumerRecord<String, String> message) {
        System.out.println("Got this message: " + message.key() + " | " + message.value());
    }
}
