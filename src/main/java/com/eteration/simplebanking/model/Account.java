package com.eteration.simplebanking.model;


import com.eteration.simplebanking.enums.PhoneProviderEnum;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// This class is a place holder you can change the complete implementation
@Entity
@Table(name = "ACCOUNT")

public class Account {

    public Account() {
    }

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BALANCE", nullable = false)
    private double balance = 0;

    @Column(name = "OWNER", length = 200, nullable = false)
    private String owner;

    @Column(name = "ACCOUNT_NUMBER", length = 10, nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "PHONE_NUMBER", length = 10, unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER")
    private PhoneProviderEnum providerEnum;

    @Version
    @Column(name = "VERSION", nullable = false)
    int version = 0;

    @OrderBy("date DESC")
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > this.balance) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
        }
        this.balance -= amount;
    }

    public void post(Transaction transaction) throws InsufficientBalanceException {
        if (transaction instanceof DepositTransaction) {
            deposit(transaction.getAmount());
        } else if (transaction instanceof WithdrawalTransaction) {
            withdraw(transaction.getAmount());
        } else {
            throw new IllegalArgumentException("Unsupported transaction type.");
        }
        transaction.setAccount(this);
        this.transactions.add(transaction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 && Objects.equals(id, account.id) && Objects.equals(owner, account.owner) && Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, owner, accountNumber);
    }

    public Long getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneProviderEnum getProviderEnum() {
        return providerEnum;
    }

    public void setProviderEnum(PhoneProviderEnum providerEnum) {
        this.providerEnum = providerEnum;
    }
}
