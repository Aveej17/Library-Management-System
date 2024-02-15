package com.jeeva.LibraryManagementSystem.repository;

import com.jeeva.LibraryManagementSystem.model.Txn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxnRepository extends JpaRepository<Txn, Integer> {

    Txn findByTxnId(String txnId);
}