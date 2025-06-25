package com.protim.service.loan.dao;

import com.protim.service.loan.db.UserLoanDocument;
import com.protim.service.loan.db.UserLoanEntity;

public record UserLoanDao (String userId, String userName){
    public UserLoanEntity toEntity(){
        UserLoanEntity entity = new UserLoanEntity();
        entity.setUserId(this.userId);
        entity.setUserName(this.userName);
        entity.setLoanStatus("APPLIED");
        return entity;
    }

    public UserLoanDocument toDocument(){
        UserLoanDocument document = new UserLoanDocument();
        document.setUserId(this.userId);
        document.setUserName(this.userName);
        document.setLoanStatus("APPLIED");
        return document;
    }

    public static UserLoanDao getDaoFromDocument(UserLoanDocument document){
        return new UserLoanDao(document.getUserId(), document.getUserName());
    }

    public static UserLoanDao getDaoFromEntity(UserLoanEntity entity){
        return new UserLoanDao(entity.getUserId(), entity.getUserName());
    }
}
