package com.eteration.simplebanking.services;

import com.eteration.simplebanking.Data.AccountDto;
import com.eteration.simplebanking.Data.TransactionDto;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.Transaction;

import java.util.List;

public interface AccountServiceInterface {

    /**
     * Finds the account by accountNumber
     *
     * @param accountNumber
     * @return Account
     */
    Account findAccount(String accountNumber);

    /**
     * Get all the accounts exist
     *
     * @return List<Account>
     */
    List<Account> getAllAccounts();

    /**
     * Do the action of credit or debit decided by transaction type
     *
     * @param accountNumber
     * @param transactionDto
     * @return Account
     */
    TransactionStatus post(String accountNumber, TransactionDto transactionDto);

    /**
     * save or create account
     *
     * @param accountDto
     * @return Account
     */
    Account saveAccount(AccountDto accountDto);

    /**
     * delete account
     *
     * @param accountNumber
     */
    void deleteAccount(String accountNumber);

    /**
     * Saves the transaction
     *
     * @param transaction
     */
    void saveTransaction(Transaction transaction);

}
