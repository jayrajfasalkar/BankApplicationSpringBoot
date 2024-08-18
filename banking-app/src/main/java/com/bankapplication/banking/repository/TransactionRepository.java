package com.bankapplication.banking.repository;

import com.bankapplication.banking.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> { //entity type and primary key type

    List<Transaction> findByAccountIdOrderByTimestampDesc(Long accountId);
}
