package com.bank.service.impl;

import com.bank.model.Account;
import com.bank.model.Recipient;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.bank.payload.request.TransactionRequest;
import com.bank.payload.request.TransferRequest;
import com.bank.repository.AccountRepo;
import com.bank.repository.RecipientRepo;
import com.bank.repository.TransactionRepo;
import com.bank.service.AccountService;
import com.bank.service.TransactionService;
import com.bank.util.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    TransactionService transactionService;

    @Autowired
    RecipientRepo recipientRepo;



    @Override
    public Account createAccount() {
        Account account = new Account();
        account.setAccountNumber(getAccountNumber());
        account.setAccountBalance(new BigDecimal(0.0));
        accountRepo.save(account);
        return accountRepo.findByAccountNumber(account.getAccountNumber());
    }

    private Long getAccountNumber() {
        long smallest = 1000_0000_0000_0000L;
        long biggest = 9999_9999_9999_9999L;
        return ThreadLocalRandom.current().nextLong(smallest, biggest);
    }

    @Override
    public void deposit(TransactionRequest request, User user) {
        Account account = user.getAccount();
        Double amount = request.getAmount();
        account.setAccountBalance(account.getAccountBalance().add(new BigDecimal(amount)));
        accountRepo.save(account);

        Date date = new Date();
        Transaction transaction = new Transaction(date, request.getDescription(),
                                                    TransactionType.DEPOSIT.toString(),
                                                    amount, account.getAccountBalance(),
                                                    false, account);
        transactionService.saveTransaction(transaction);


    }

    @Override
    public void withdraw(TransactionRequest request, User user) {
        Account account = user.getAccount();
        Double amount = request.getAmount();
        account.setAccountBalance(account.getAccountBalance().subtract(new BigDecimal(amount)));
        accountRepo.save(account);

        Date date = new Date();
        Transaction transaction = new Transaction(date, request.getDescription(),
                TransactionType.WITHDRAW.name(),
                amount, account.getAccountBalance(),
                false, account);
        transactionService.saveTransaction(transaction);

    }

    @Override
    public void saveRecipient(Recipient recipient) {
        recipientRepo.save(recipient);
    }

    @Override
    public void transfer(TransferRequest request, User user) {
        Account account = user.getAccount();
        Double amount = request.getAmount();
        account.setAccountBalance(account.getAccountBalance().subtract(new BigDecimal(amount)));
        accountRepo.save(account);

        Date date = new Date();
        Transaction transaction = new Transaction(date, request.getRecipientName(),
                TransactionType.TRANSFER.name(),
                amount, account.getAccountBalance(),
                true, account);
        transactionService.saveTransaction(transaction);

    }


}
