package com.protim.service.loan.repository;

import com.protim.service.loan.db.UserLoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanJpaRepository extends JpaRepository<UserLoanEntity, String> {
}
