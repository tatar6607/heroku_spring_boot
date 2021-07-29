package com.bank.service;

import com.bank.model.Transaction;

import java.util.List;

public interface TransactionService {
    void saveTransaction(Transaction transaction);
    List<Transaction> getAllTransaction(Long accountId);

}
