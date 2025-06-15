package com.protim.service.credit.controller;

import com.protim.service.credit.repository.CreditReportRepository;
import com.protim.service.credit.dao.CreditReportErrorResponse;
import com.protim.service.credit.entity.CreditReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestController
@RequestMapping("/creditservice")
public record CreditReportController(CreditReportRepository repository){

    @PostMapping("/create")
    public ResponseEntity<CreditReport> createNewReport(@RequestBody CreditReport creditReport){
        return ResponseEntity.accepted().body(repository.save(creditReport));
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<CreditReport> fetchReport(@RequestParam String userId){
        CreditReport report = repository.findById(userId).orElseThrow(
                () -> new HttpClientErrorException(
                        HttpStatus.BAD_REQUEST,
                        "Data not available for user ID " + userId));
        System.out.println("This is the report: " + report);
        return ResponseEntity.ok().body(report);
    }

    @ExceptionHandler(exception = HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CreditReportErrorResponse> handleClientErrors(HttpClientErrorException e){
        log.error("Exception in controller: {}-{}", e.getClass().getSimpleName(), e.getMessage());
        return ResponseEntity.badRequest().body(new CreditReportErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Invalid details"));
    }
}
