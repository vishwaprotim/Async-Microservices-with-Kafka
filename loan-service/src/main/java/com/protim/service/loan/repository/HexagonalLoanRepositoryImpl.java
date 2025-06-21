package com.protim.service.loan.repository;

import com.protim.service.loan.dao.UserLoanDao;
import com.protim.service.loan.entity.UserLoan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HexagonalLoanRepositoryImpl implements HexagonalLoanRepository{

    @Override
    public UserLoan save(UserLoanDao userLoanDao) {
        return repository.save(userLoanDao.toEntity());
    }

    @Override
    public Optional<UserLoan> findById(String id) {
        return repository.findById(id);
    }

    @Autowired LoanRepository repository;


}
