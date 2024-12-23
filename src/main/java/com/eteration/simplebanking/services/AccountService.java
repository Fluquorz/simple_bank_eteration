package com.eteration.simplebanking.services;


import com.eteration.simplebanking.Data.AccountDto;
import com.eteration.simplebanking.Data.TransactionDto;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.enums.ActionType;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.repository.AccountRepositoryInterface;
import com.eteration.simplebanking.repository.DepositTransactionRepositoryInterface;
import com.eteration.simplebanking.repository.PhoneBillPaymentTransactionRepositoryInterface;
import com.eteration.simplebanking.repository.WithDrawalTransactionRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// This class is a place holder you can change the complete implementation
@Service("accountServiceInterface")
@Transactional(readOnly = true)
public class AccountService implements AccountServiceInterface {

    @Autowired
    private AccountRepositoryInterface accountRepositoryInterface;
    @Autowired
    private WithDrawalTransactionRepositoryInterface withDrawalTransactionRepositoryInterface;
    @Autowired
    private DepositTransactionRepositoryInterface depositTransactionRepositoryInterface;
    @Autowired
    private PhoneBillPaymentTransactionRepositoryInterface phoneBillPaymentTransactionRepositoryInterface;

    @Override
    public Account findAccount(String accountNumber) {
        try {
            Account account = StringUtils.hasText(accountNumber) ? accountRepositoryInterface.findByAccountNumber(accountNumber) : null;
            if (account == null) {
                return new Account();
            }
            return account;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while finding the account: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        try {
            List<Account> accountList = accountRepositoryInterface.findAll();
            if (CollectionUtils.isEmpty(accountList)) {
                return new ArrayList<>();
            }
            return accountList;

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while finding the account: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    public TransactionStatus post(String accountNumber, TransactionDto transactionDto) {
        try {
            Account account = findAccount(accountNumber);
            if (account == null) {
                throw new RuntimeException("Account not found for account number: " + accountNumber);
            }
            if (account.getPhoneNumber() != null) {
                transactionDto.setPhoneNumber(account.getPhoneNumber());
                transactionDto.setPhoneProviderEnum(account.getProviderEnum());
            }
            Transaction transaction = createTransaction(transactionDto);
            transaction.setAccount(account);
            if (transaction instanceof DepositTransaction) {
                bankAction(transaction, ActionType.CREDIT);
            } else if (transaction instanceof WithdrawalTransaction) {
                bankAction(transaction, ActionType.DEBIT);
            } else if (transaction instanceof PhoneBillPaymentTransaction) {
                bankAction(transaction, ActionType.DEBIT);
            }
            transaction.setApprovalCode(UUID.randomUUID().toString());
            account.getTransactions().add(transaction);
            accountRepositoryInterface.save(account);
            return new TransactionStatus("OK", transaction.getApprovalCode(), null, transaction.getAccount());

        } catch (Exception e) {
            return new TransactionStatus("FAILED", null, e.getMessage(), null);
        }
    }

    @Transactional(readOnly = false)
    @Override
    public Account saveAccount(AccountDto accountDto) {
        try {
            Account account = new Account();
            if (StringUtils.hasText(accountDto.getAccountNumber())) {
                account = findAccount(accountDto.getAccountNumber());
                if (account.getId() != null) {
                    return account;
                }
            }
            account.setAccountNumber(accountDto.getAccountNumber());
            account.setBalance(accountDto.getBalance());
            account.setOwner(accountDto.getOwnerName());
            if (StringUtils.hasText(accountDto.getPhoneNumber()) && accountDto.getProvider() != null) {
                account.setPhoneNumber(accountDto.getPhoneNumber());
                account.setProviderEnum(accountDto.getProvider());
            }
            return accountRepositoryInterface.save(account);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the account: " + e.getMessage(), e);
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteAccount(String accountNumber) {
        try {
            Account account = findAccount(accountNumber);
            if (account.getId() != null) {
                accountRepositoryInterface.delete(account);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the account: " + e.getMessage(), e);
        }
    }


    @Override
    @Transactional(readOnly = false)
    public void saveTransaction(Transaction transaction) {
        try {
            if (transaction != null) {
                if (transaction instanceof DepositTransaction) {
                    depositTransactionRepositoryInterface.save((DepositTransaction) transaction);
                } else if (transaction instanceof WithdrawalTransaction) {
                    withDrawalTransactionRepositoryInterface.save((WithdrawalTransaction) transaction);
                } else if (transaction instanceof PhoneBillPaymentTransaction) {
                    phoneBillPaymentTransactionRepositoryInterface.save((PhoneBillPaymentTransaction) transaction);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the account: " + e.getMessage(), e);
        }
    }

    /**
     * Creates the transaction for the approved transaction type
     *
     * @param transactionDto
     * @return Transaction<>
     */
    private Transaction createTransaction(TransactionDto transactionDto) {
        switch (transactionDto.getType()) {
            case DEPOSIT:
                return new DepositTransaction(transactionDto.getAmount());
            case WITHDRAW:
                return new WithdrawalTransaction(transactionDto.getAmount());
            case PHONE_BILL:
                if (!StringUtils.hasText(transactionDto.getPhoneNumber())) {
                    throw new IllegalArgumentException("Phone number is required for PHONE_BILL transactions.");
                }
                return new PhoneBillPaymentTransaction(transactionDto.getAmount(), transactionDto.getPhoneNumber(), transactionDto.getPhoneProviderEnum());
            default:
                throw new IllegalArgumentException("Unsupported transaction type: " + transactionDto.getType());
        }
    }


    /**
     * This method does the debit or credit actions deciding with action type.
     *
     * @param transaction
     * @param type
     */
    private void bankAction(Transaction transaction, ActionType type) {
        try {
            Double accountBalance = transaction.getAccount().getBalance();
            Double transactionAmount = transaction.getAmount();
            if (ActionType.DEBIT.equals(type)) {
                if (transactionAmount > accountBalance) {
                    throw new InsufficientBalanceException("Insufficient balance for transaction.");
                }
                transaction.getAccount().setBalance(accountBalance - transactionAmount);
            }
            if (ActionType.CREDIT.equals(type)) {
                transaction.getAccount().setBalance(accountBalance + transactionAmount);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during the transaction: " + e.getMessage(), e);
        }
    }
}
