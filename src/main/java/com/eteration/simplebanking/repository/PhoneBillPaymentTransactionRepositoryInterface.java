package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.PhoneBillPaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneBillPaymentTransactionRepositoryInterface extends JpaRepository<PhoneBillPaymentTransaction, Long> {
}
