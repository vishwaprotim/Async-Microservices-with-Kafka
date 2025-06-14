package com.protim.service.loan.service;

import com.protim.service.loan.dao.UserLoanDao;
import com.protim.service.loan.entity.UserLoan;
import com.protim.service.loan.repository.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;


@Slf4j
@Service
public record LoanService(LoanRepository repository,
                          KafkaTemplate<String, String> kafkaTemplate,
                          @Value("${spring.kafka.producer.topic}") String topic) {

    public UserLoan applyLoan(UserLoanDao userLoanDao){
        UserLoan userLoan = repository.save(userLoanDao.toEntity());
        log.info("This is the topic name: {}", topic);
        kafkaTemplate.send(topic, userLoan.getUserId(), userLoan.getLoanId());
        return userLoan;
    }

    public UserLoan getLoanStatus(String loanId){
        return repository.findById(loanId)
                        .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Not found!"));
    }
}
