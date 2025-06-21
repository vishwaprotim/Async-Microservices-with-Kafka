package com.protim.service.loan.kafka;

public interface HexagonalKafkaTemplate {

    void send(String key, String value);
}
