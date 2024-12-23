package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.DepositTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositTransactionRepositoryInterface extends JpaRepository<DepositTransaction, Long> {
}
