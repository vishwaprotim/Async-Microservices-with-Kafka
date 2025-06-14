package com.protim.service.loan.dao;

import com.protim.service.loan.entity.UserLoan;

public record UserLoanDao (String userId, String userName){
    public UserLoan toEntity(){
        UserLoan entity = new UserLoan();
        entity.setUserId(this.userId);
        entity.setUserName(this.userName);
        entity.setLoanStatus("APPLIED");
        return entity;
    }
}
