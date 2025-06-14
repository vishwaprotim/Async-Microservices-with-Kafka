package com.protim.service.loan.controller;


import com.protim.service.loan.dao.LoanErrorResponse;
import com.protim.service.loan.dao.LoanResponse;
import com.protim.service.loan.dao.UserLoanDao;
import com.protim.service.loan.entity.UserLoan;
import com.protim.service.loan.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestController
@RequestMapping("/loanservice")
public record LoanController (LoanService service){

    @PostMapping("/apply")
    public ResponseEntity<LoanResponse> applyLoan(@RequestBody UserLoanDao userLoanDao){
        return ResponseEntity.accepted().body(new LoanResponse(
                HttpStatus.ACCEPTED,
                "Loan Application placed successfully",
                service.applyLoan(userLoanDao)));
    }

    @GetMapping("/status")
    public ResponseEntity<UserLoan> getLoanStatus(@RequestParam String loanId){
        return ResponseEntity.ok().body(service.getLoanStatus(loanId));
    }

    @ExceptionHandler(exception = HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<LoanErrorResponse> handleClientErrors(HttpClientErrorException e){
        log.error("Exception in controller: {}-{}", e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.badRequest().body(new LoanErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid details"));
    }
}
