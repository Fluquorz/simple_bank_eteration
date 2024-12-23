package com.eteration.simplebanking.model;

import com.eteration.simplebanking.enums.PhoneProviderEnum;
import com.eteration.simplebanking.enums.TransactionType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;
@Entity
@Table(name = "PHONE_BILL_TRANSACTION")
public class PhoneBillPaymentTransaction extends Transaction{
    public PhoneBillPaymentTransaction() {
        super();
    }

    public PhoneBillPaymentTransaction(double amount, String phoneNumber, PhoneProviderEnum phoneProviderEnum) {
        super(amount, TransactionType.PHONE_BILL);
        this.phoneNumber = phoneNumber;
        this.provider = phoneProviderEnum;
    }

    @Column(name = "PROVIDER", nullable = false)
    private PhoneProviderEnum provider;

    @Column(name = "PHONE_NUMBER", nullable = false, length = 10)
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PhoneBillPaymentTransaction that = (PhoneBillPaymentTransaction) o;
        return provider == that.provider && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), provider, phoneNumber);
    }

    public PhoneProviderEnum getProvider() {
        return provider;
    }

    public void setProvider(PhoneProviderEnum provider) {
        this.provider = provider;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
