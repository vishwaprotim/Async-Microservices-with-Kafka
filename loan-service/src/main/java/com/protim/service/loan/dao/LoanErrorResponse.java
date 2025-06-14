package com.protim.service.loan.dao;

import org.springframework.http.HttpStatus;

public record LoanErrorResponse(HttpStatus status, String message) {}
