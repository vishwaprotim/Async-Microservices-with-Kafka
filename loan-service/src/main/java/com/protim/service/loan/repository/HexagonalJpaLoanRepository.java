package com.protim.service.loan.repository;

import com.protim.service.loan.dao.UserLoanDao;
import com.protim.service.loan.db.UserLoan;
import com.protim.service.loan.db.UserLoanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Lazy
@Component
//@Primary // Activate this to use JPA (Postgres)
public class HexagonalJpaLoanRepository implements HexagonalLoanRepository{

    @Autowired
    LoanJpaRepository repository;

    @Override
    public UserLoan saveDao(UserLoanDao userLoanDao) {
        UserLoanEntity entity = repository.save(userLoanDao.toEntity());
        return UserLoan.fromEntity(entity);
    }

    @Override
    public UserLoan save(UserLoan userLoan) {
        UserLoanEntity entity = repository.save(UserLoan.createEntity(userLoan));
        return UserLoan.fromEntity(entity);
    }

    @Override
    public Optional<UserLoan> findById(String id) {
        Optional<UserLoanEntity> userLoan = repository.findById(id);
        return userLoan.map(UserLoan::fromEntity);
    }


}
