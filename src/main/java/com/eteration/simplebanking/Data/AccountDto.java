package com.eteration.simplebanking.Data;

import com.eteration.simplebanking.enums.PhoneProviderEnum;
import com.eteration.simplebanking.model.Account;


public class AccountDto {
    private String accountNumber;
    private String ownerName;
    private Double balance;
    private String phoneNumber;
    private PhoneProviderEnum provider;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneProviderEnum getProvider() {
        return provider;
    }

    public void setProvider(PhoneProviderEnum provider) {
        this.provider = provider;
    }



}
