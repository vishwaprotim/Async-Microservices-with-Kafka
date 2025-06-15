package com.protim.service.credit.dao;

import org.springframework.http.HttpStatus;

public record CreditReportErrorResponse (HttpStatus status, String message){
}
