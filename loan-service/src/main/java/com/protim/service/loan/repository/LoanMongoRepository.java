package com.protim.service.loan.repository;

import com.protim.service.loan.db.UserLoanDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoanMongoRepository extends MongoRepository<UserLoanDocument, String> {
}
