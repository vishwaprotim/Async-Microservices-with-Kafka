package com.protim.service.loan.kafka;

import com.protim.service.loan.entity.UserLoan;
import com.protim.service.loan.repository.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public record CreditReportListener(LoanRepository repository) {

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void listen(ConsumerRecord<String, String> message) throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> processLoanApplication(message.key(), message.value()));
        log.info("\nRead message: Loan Application ID: {} | Status: {}\nSuccessfully updated the status for application {}", message.key(), message.value(), future.get());
    }

    String processLoanApplication(String loanApplicationId, String status) {
        try{
            Thread.sleep(1000); // Increase the delay to 20 seconds for Demo
        } catch (InterruptedException e){
            log.error("Cannot hold the loan process", e);
        }
        UserLoan loanApplication = repository.findById(loanApplicationId).orElseThrow(
                () -> new UnsupportedOperationException("Cannot find application id: " + loanApplicationId)
        );

        loanApplication.setLoanStatus(status);
        repository.save(loanApplication);
        return loanApplicationId;
    }
}
