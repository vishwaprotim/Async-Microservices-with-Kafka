package com.protim.service.loan.service;

import com.protim.service.loan.dao.UserLoanDao;
import com.protim.service.loan.entity.UserLoan;
import com.protim.service.loan.kafka.HexagonalKafkaTemplate;
import com.protim.service.loan.repository.HexagonalLoanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;



public record HexagonalLoanService(HexagonalLoanRepository repository,
                                   HexagonalKafkaTemplate kafkaTemplate) {

    public UserLoan applyLoan(UserLoanDao userLoanDao){
        UserLoan userLoan = repository.save(userLoanDao);
        kafkaTemplate.send(userLoan.getUserId(), userLoan.getLoanId());
        return userLoan;
    }

    public UserLoan getLoanStatus(String loanId){
        return repository.findById(loanId)
                        .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Not found!"));
    }
}
