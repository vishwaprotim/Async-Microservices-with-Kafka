package com.protim.service.loan.repository;

import com.protim.service.loan.dao.UserLoanDao;
import com.protim.service.loan.db.UserLoan;

import java.util.Optional;

public interface HexagonalLoanRepository {

    UserLoan saveDao(UserLoanDao userLoanDao);
    UserLoan save(UserLoan userLoan);
    Optional<UserLoan> findById(String id);
}
