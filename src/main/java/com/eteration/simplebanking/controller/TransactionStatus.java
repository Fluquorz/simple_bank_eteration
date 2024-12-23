package com.eteration.simplebanking.controller;


// This class is a place holder you can change the complete implementation

import com.eteration.simplebanking.model.Account;

public class TransactionStatus {
    private Account account;
    private String status;
    private String approvalCode;
    private String errorMessage;

    public TransactionStatus() {
    }

    public TransactionStatus(String status, String approvalCode, String errorMessage, Account account) {
        this.account = account;
        this.status = status;
        this.approvalCode = approvalCode;
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
