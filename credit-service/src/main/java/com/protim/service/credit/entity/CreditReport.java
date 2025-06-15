package com.protim.service.credit.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "credit_report")
public class CreditReport {

    @Id
    String _id;
    int creditScore;
    String report;
}
