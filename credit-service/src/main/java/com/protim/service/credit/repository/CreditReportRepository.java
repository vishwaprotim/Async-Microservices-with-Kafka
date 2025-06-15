package com.protim.service.credit.repository;

import com.protim.service.credit.entity.CreditReport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditReportRepository extends MongoRepository<CreditReport, String> {
}
