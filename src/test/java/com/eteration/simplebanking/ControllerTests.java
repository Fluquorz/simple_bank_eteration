package com.eteration.simplebanking;

import com.eteration.simplebanking.Data.AccountDto;
import com.eteration.simplebanking.Data.TransactionDto;
import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.enums.PhoneProviderEnum;
import com.eteration.simplebanking.enums.TransactionType;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.repository.AccountRepositoryInterface;
import com.eteration.simplebanking.services.AccountServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTests {

    @Mock
    private AccountServiceInterface accountServiceInterface;

    @Autowired
    private AccountRepositoryInterface accountRepositoryInterface;

    @InjectMocks
    private AccountController accountController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setupDatabaseForTest() {
        String accountNumber = MockConstant.MOCK_ACCOUNT_NUMBER;
        String createdByExistingAccountNumber = MockConstant.CREATED_MOCK_ACCOUNT_NUMBER;

        Account existingAccount = accountRepositoryInterface.findByAccountNumber(accountNumber);
        Account createdByExistingAccount = accountRepositoryInterface.findByAccountNumber(createdByExistingAccountNumber);

        Account account;
        if (existingAccount != null) {
            account = existingAccount;
            account.setBalance(100.0);
            account.setPhoneNumber(MockConstant.MOCK_ACCOUNT_PHONE_NUMBER);
            account.setProviderEnum(PhoneProviderEnum.VODAPHONE);
            account.setTransactions(new ArrayList<>());
        } else {
            account = new Account("Ahmet Emin Kahraman", accountNumber);
            account.setBalance(100.0);
            account.setPhoneNumber(MockConstant.MOCK_ACCOUNT_PHONE_NUMBER);
            account.setProviderEnum(PhoneProviderEnum.VODAPHONE);
            account.setTransactions(new ArrayList<>());
        }

        if (createdByExistingAccount != null) {
            accountRepositoryInterface.delete(createdByExistingAccount);
        }

        accountRepositoryInterface.save(account);
    }

    @Test
    void testGetAccount() throws Exception {
        Account mockAccount = new Account("Ahmet Emin Kahraman", MockConstant.MOCK_ACCOUNT_NUMBER);
        mockAccount.setBalance(100.0);

        Mockito.when(accountServiceInterface.findAccount(MockConstant.MOCK_ACCOUNT_NUMBER)).thenReturn(mockAccount);

        mockMvc.perform(get("/account/v1/get/9999999999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(MockConstant.MOCK_ACCOUNT_NUMBER))
                .andExpect(jsonPath("$.balance").value(100.0))
                .andExpect(jsonPath("$.owner").value("Ahmet Emin Kahraman"));
    }

    @Test
    void testPostTransactionDeposit() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(50.0);
        transactionDto.setType(TransactionType.DEPOSIT);

        TransactionStatus transactionStatus = new TransactionStatus("OK", UUID.randomUUID().toString(), null, null);

        Mockito.when(accountServiceInterface.post(anyString(), any(TransactionDto.class))).thenReturn(transactionStatus);

        mockMvc.perform(post("/account/v1/post/9999999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":50.0,\"type\":\"DEPOSIT\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.account.balance").value(150.0));
    }

    @Test
    void testPostTransactionWithdraw() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(50.0);
        transactionDto.setType(TransactionType.WITHDRAW);

        TransactionStatus transactionStatus = new TransactionStatus("OK", UUID.randomUUID().toString(), null, null);

        Mockito.when(accountServiceInterface.post(anyString(), any(TransactionDto.class))).thenReturn(transactionStatus);

        mockMvc.perform(post("/account/v1/post/9999999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":50.0,\"type\":\"WITHDRAW\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.account.balance").value(50.0));
    }

    @Test
    void testPostTransactionPhoneBillPayment() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(25.0);
        transactionDto.setType(TransactionType.PHONE_BILL);

        TransactionStatus transactionStatus = new TransactionStatus("OK", UUID.randomUUID().toString(), null, null);

        Mockito.when(accountServiceInterface.post(anyString(), any(TransactionDto.class))).thenReturn(transactionStatus);

        mockMvc.perform(post("/account/v1/post/9999999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":25.0,\"type\":\"PHONE_BILL\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.account.balance").value(75.0));
    }

    @Test
    void testCreateOrUpdateAccount() throws Exception {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(MockConstant.CREATED_MOCK_ACCOUNT_NUMBER);
        accountDto.setBalance(200.0);
        accountDto.setOwnerName("Ahmet Kahraman");

        Account mockAccount = new Account("Ahmet Emin Kahraman", MockConstant.CREATED_MOCK_ACCOUNT_NUMBER);
        mockAccount.setBalance(200.0);

        Mockito.when(accountServiceInterface.saveAccount(any(AccountDto.class))).thenReturn(mockAccount);

        mockMvc.perform(post("/account/v1/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"9999999998\",\"balance\":200.0,\"ownerName\":\"Ahmet Emin Kahraman\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber").value(MockConstant.CREATED_MOCK_ACCOUNT_NUMBER))
                .andExpect(jsonPath("$.balance").value(200.0))
                .andExpect(jsonPath("$.owner").value("Ahmet Emin Kahraman"));
    }

    @Test
    void testDeleteAccount() throws Exception {
        Mockito.doNothing().when(accountServiceInterface).deleteAccount(MockConstant.MOCK_ACCOUNT_NUMBER);

        mockMvc.perform(delete("/account/v1/delete/9999999999"))
                .andExpect(status().isOk())
                .andExpect(content().string("Account with account number 9999999999 deleted successfully."));
    }

}
