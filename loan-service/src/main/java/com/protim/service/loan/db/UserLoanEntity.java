package com.protim.service.loan.db;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_loan")
public class UserLoanEntity {

    @Column(name = "loan_id", length = 10)
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    String loanId;
    @Column(name = "user_id", length = 10)
    String userId;
    @Column(name = "user_name", length = 255)
    String userName;
    @Column(name = "loan_status", length = 10)
    String loanStatus;
}
