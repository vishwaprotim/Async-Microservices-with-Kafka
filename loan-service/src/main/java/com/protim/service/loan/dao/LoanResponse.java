package com.protim.service.loan.dao;


import com.protim.service.loan.db.UserLoan;
import org.springframework.http.HttpStatus;

public record LoanResponse(HttpStatus status,
                           String message,
                           UserLoan userLoan) {}
