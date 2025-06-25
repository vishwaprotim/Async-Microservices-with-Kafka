package com.protim.service.loan.db;

public record UserLoan(
        String loanId,
        String userId,
        String userName,
        String loanStatus) {

    public static UserLoan fromEntity(UserLoanEntity entity){
        return new UserLoan(
                entity.getLoanId(),
                entity.getUserId(),
                entity.getUserName(),
                entity.getLoanStatus());
    }

    public static UserLoan fromDocument(UserLoanDocument document){
        return new UserLoan(
                document.getLoanId(),
                document.getUserId(),
                document.getUserName(),
                document.getLoanStatus());
    }

    public static UserLoanEntity createEntity(UserLoan userLoan){
        UserLoanEntity entity = new UserLoanEntity();
        entity.setUserId(userLoan.userId());
        entity.setUserName(userLoan.userName());
        entity.setLoanId(userLoan.loanId());
        entity.setLoanStatus(userLoan.loanStatus());
        return entity;
    }

    public static UserLoanDocument createDocument(UserLoan userLoan){
        UserLoanDocument document = new UserLoanDocument();
        document.setUserId(userLoan.userId());
        document.setUserName(userLoan.userName());
        document.setLoanId(userLoan.loanId());
        document.setLoanStatus(userLoan.loanStatus());
        return document;
    }
}
