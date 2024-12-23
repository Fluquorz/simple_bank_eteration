package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.Data.AccountDto;
import com.eteration.simplebanking.Data.TransactionDto;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.services.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This class is a place holder you can change the complete implementation
@RestController
@RequestMapping("/account/v1")
public class AccountController {

    @Autowired
    private AccountServiceInterface accountServiceInterface;

    @GetMapping("/get/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        try {
            Account account = accountServiceInterface.findAccount(accountNumber);
            if(account.getId() == null) {
                throw new RuntimeException("There is no such an account exist");
            }
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<Account>> getAllAccounts() {
        try {
            List<Account> account = accountServiceInterface.getAllAccounts();
            if(CollectionUtils.isEmpty(account)) {
                throw new RuntimeException("There is no account exist");
            }
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/post/{accountNumber}")
    public ResponseEntity<TransactionStatus> post(@PathVariable String accountNumber, @RequestBody TransactionDto transactiondto) {
        TransactionStatus transactionStatus = accountServiceInterface.post(accountNumber, transactiondto);
        if ("OK".equals(transactionStatus.getStatus())) {
            return ResponseEntity.ok(transactionStatus);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionStatus);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Account> createOrUpdateAccount(@RequestBody AccountDto accountDto) {
        try {
            Account account = accountServiceInterface.saveAccount(accountDto);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber) {
        try {
            accountServiceInterface.deleteAccount(accountNumber);
            return ResponseEntity.ok("Account with account number " + accountNumber + " deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Account with account number " + accountNumber + " not found.");
        }
    }
}