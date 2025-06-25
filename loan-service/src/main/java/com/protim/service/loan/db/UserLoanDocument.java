package com.protim.service.loan.db;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user_loan")
public class UserLoanDocument {

    @Id
    String loanId;
    String userId;
    String userName;
    String loanStatus;
}
