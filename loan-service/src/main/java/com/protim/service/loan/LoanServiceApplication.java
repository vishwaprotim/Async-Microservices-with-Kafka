package com.protim.service.loan;

import com.protim.service.loan.kafka.HexagonalKafkaTemplate;
import com.protim.service.loan.repository.HexagonalLoanRepository;
import com.protim.service.loan.service.HexagonalLoanService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableMongoRepositories
public class LoanServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanServiceApplication.class, args);
	}

	@Bean("HexagonalLoanService")
	public HexagonalLoanService loanService(HexagonalLoanRepository repository, HexagonalKafkaTemplate kafkaTemplate){
		return new HexagonalLoanService(repository, kafkaTemplate);
	}

}
