package com.protim.service.loan.repository;

import com.protim.service.loan.entity.UserLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<UserLoan, String> {
}
