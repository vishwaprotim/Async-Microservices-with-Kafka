package com.protim.service.loan.service;

import com.protim.service.loan.dao.UserLoanDao;
import com.protim.service.loan.db.UserLoanEntity;
import com.protim.service.loan.repository.LoanJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;


@Slf4j
@Service
public record LoanService(LoanJpaRepository repository,
                          KafkaTemplate<String, String> kafkaTemplate,
                          @Value("${spring.kafka.producer.topic}") String topic) {

    public UserLoanEntity applyLoan(UserLoanDao userLoanDao){
        UserLoanEntity userLoan = repository.save(userLoanDao.toEntity());
        kafkaTemplate.send(topic, userLoan.getUserId(), userLoan.getLoanId());
        log.info("\nLoan Application placed successfully: {}", userLoan.getLoanId());
        return userLoan;
    }

    public UserLoanEntity getLoanStatus(String loanId){
        return repository.findById(loanId)
                        .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Not found!"));
    }
}
