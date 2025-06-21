package com.protim.service.loan.repository;

import com.protim.service.loan.dao.UserLoanDao;
import com.protim.service.loan.entity.UserLoan;

import java.util.Optional;

public interface HexagonalLoanRepository {

    UserLoan save(UserLoanDao userLoanDao);
    Optional<UserLoan> findById(String id);
}
