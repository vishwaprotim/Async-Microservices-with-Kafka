package com.protim.service.loan.repository;

import com.protim.service.loan.dao.UserLoanDao;
import com.protim.service.loan.db.UserLoan;
import com.protim.service.loan.db.UserLoanDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Lazy
@Component
@Primary // Activate this to use MongoDB
public class HexagonalMongoLoanRepository implements HexagonalLoanRepository{

    @Autowired
    LoanMongoRepository repository;

    @Override
    public UserLoan saveDao(UserLoanDao userLoanDao) {
        UserLoanDocument document = repository.save(userLoanDao.toDocument());
        return UserLoan.fromDocument(document);
    }

    @Override
    public UserLoan save(UserLoan userLoan) {
        UserLoanDocument document = UserLoan.createDocument(userLoan);
        return UserLoan.fromDocument(repository.save(UserLoan.createDocument(userLoan)));
    }

    @Override
    public Optional<UserLoan> findById(String id) {
        Optional<UserLoanDocument> userLoanDocument = repository.findById(id);
        return userLoanDocument.map(UserLoan::fromDocument);
    }


}
