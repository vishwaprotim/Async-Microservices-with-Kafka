package com.protim.service.loan.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class HexagonalKafkaTemplateImpl implements HexagonalKafkaTemplate {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    String topic;


    @Override
    public void send(String key, String value) {
        kafkaTemplate.send(topic, key, value);
    }
}
