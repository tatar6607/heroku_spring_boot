package com.bank.service.impl;

import com.bank.model.Transaction;
import com.bank.repository.TransactionRepo;
import com.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public List<Transaction> getTransactionByDate(Date date) {
        return transactionRepo.getTransactionByDate(date);
    }

    @Override
    public List<Transaction> getTransactionByAccountAndDate(Long accountId, Date date) {
        return transactionRepo.getTransactionByAccountAndDate(accountId,date);
    }

    @Override
    public List<Transaction> getTransactionsByDateIsBetweenAndAccount_Id(Date start, Date end, Long account_id) {
        return transactionRepo.getTransactionsByDateIsBetweenAndAccount_Id(start,end,account_id);
    }

    @Override
    public List<Transaction> getAll() {
        return (List<Transaction>) transactionRepo.findAll();
    }


}
