package com.bank.service.impl;

import com.bank.model.Transaction;
import com.bank.repository.TransactionRepo;
import com.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepo transactionRepo;

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepo.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransaction(Long accountId) {
        return transactionRepo.getAllByAccount_Id(accountId);
    }



}
