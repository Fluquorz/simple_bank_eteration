package com.eteration.simplebanking.Data;

import com.eteration.simplebanking.enums.PhoneProviderEnum;
import com.eteration.simplebanking.enums.TransactionType;
import com.eteration.simplebanking.model.PhoneBillPaymentTransaction;
import com.eteration.simplebanking.model.Transaction;

public class TransactionDto {
    private Double amount;
    private TransactionType type;
    private String accountNumber;
    private String phoneNumber;
    private PhoneProviderEnum phoneProviderEnum;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneProviderEnum getPhoneProviderEnum() {
        return phoneProviderEnum;
    }

    public void setPhoneProviderEnum(PhoneProviderEnum phoneProviderEnum) {
        this.phoneProviderEnum = phoneProviderEnum;
    }
}
