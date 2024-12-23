package com.eteration.simplebanking.model;


import com.eteration.simplebanking.enums.TransactionType;

import javax.persistence.Entity;

// This class is a place holder you can change the complete implementation
@Entity
public class DepositTransaction extends Transaction  {
    public DepositTransaction() {
        super();
    }

    public DepositTransaction(Double amount) {
        super(amount, TransactionType.DEPOSIT);
    }
}
