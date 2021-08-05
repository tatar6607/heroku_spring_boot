package com.bank.service;

import com.bank.model.Transaction;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    void saveTransaction(Transaction transaction);
    List<Transaction> getAllTransaction(Long accountId);
    List<Transaction> getTransactionByDate(Date date);
    List<Transaction> getTransactionByAccountAndDate(Long accountId, Date date);
    List<Transaction> getTransactionsByDateIsBetweenAndAccount_Id(Date start, Date end, Long account_id);
    List<Transaction> getAll();

}
