package com.protim.service.credit.kafka;

import com.protim.service.credit.dao.ApplicationStatus;
import com.protim.service.credit.entity.CreditReport;
import com.protim.service.credit.exception.CreditReportProcessingException;
import com.protim.service.credit.repository.CreditReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record CreditCheckListener(KafkaTemplate<String, String> kafkaTemplate,
                                  @Value("${spring.kafka.producer.topic}") String topic,
                                  CreditReportRepository repository) {

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void listen(ConsumerRecord<String, String> message) {
        log.info("\nRead message: User ID: {} | Loan Application ID: {}", message.key(), message.value());
        processCreditReport(message.key(), message.value());
    }

    void processCreditReport(String userId, String loanApplicationId){
        ApplicationStatus status;
        try{
            CreditReport report  = repository.findById(userId).orElseThrow(
                    ()-> new CreditReportProcessingException("Cannot find information on this user"));
            status = report.getCreditScore() < 700? ApplicationStatus.REJECTED : ApplicationStatus.APPROVED;
            log.info("\nFetching credit report for user {}\nReport fetched successfully. Current score is {}", userId, report.getCreditScore());
        } catch (CreditReportProcessingException e){
            log.error("\nUnable to process credit report for user: {} - {}", userId, e.getMessage());
            status = ApplicationStatus.ON_HOLD;
        }
        kafkaTemplate.send(topic, loanApplicationId, status.toString());
    }
}
