package com.bank.controller;


import com.bank.dao.UserDAO;
import com.bank.model.Recipient;
import com.bank.model.User;
import com.bank.payload.request.RecipientForm;
import com.bank.payload.request.TransactionRequest;
import com.bank.payload.request.TransferRequest;
import com.bank.payload.response.TransactionResponse;
import com.bank.repository.RecipientRepo;
import com.bank.repository.UserRepo;
import com.bank.service.AccountService;

import com.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/account")

public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RecipientRepo recipientRepo;


    @PostMapping(path = "/deposit")
    public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody TransactionRequest transactionRequest) {
        TransactionResponse response = new TransactionResponse();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (transactionRequest.getAmount() <= 0) {
            response.setMessage("Amount must be greater than 0");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        accountService.deposit(transactionRequest, user);
        response.setMessage("Amount successfully deposited");
        response.setSuccess(true);
        UserDAO userDAO = userService.getUserDAOByUsername(user.getUsername());
        response.setUser(userDAO);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody TransactionRequest transactionRequest) {
        TransactionResponse response = new TransactionResponse();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null && user.getAccount() != null && transactionRequest.getAmount() > 0 &&
                user.getAccount().getAccountBalance().intValue() >= transactionRequest.getAmount()) {
            accountService.withdraw(transactionRequest, user);
            response.setMessage("Amount successfully withdrawed");
            response.setSuccess(true);
            UserDAO userDAO = userService.getUserDAOByUsername(user.getUsername());
            response.setUser(userDAO);
        } else {
            response.setMessage("Amount is not sufficient");
            response.setSuccess(false);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);


//        accountService.withdraw(transactionRequest, user);
//
//        response.setMessage("Amount successfully withdrawed");
//        response.setSuccess(true);
//        UserDAO userDAO = userService.getUserDAOByUsername(user.getUsername());
//        response.setUser(userDAO);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest transferRequest) {
//        TransactionResponse response = new TransactionResponse();
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        accountService.transfer(transferRequest, user);
//
//        response.setMessage("Amount successfully transferred");
//        response.setSuccess(true);
//        UserDAO userDAO = userService.getUserDAOByUsername(user.getUsername());
//        response.setUser(userDAO);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);

        TransactionResponse response = new TransactionResponse();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null && user.getAccount() != null && transferRequest.getAmount() > 0 &&
                user.getAccount().getAccountBalance().intValue() >= transferRequest.getAmount()) {
            accountService.transfer(transferRequest, user);
            response.setMessage("Amount succesfully transfered");
            response.setSuccess(true);
            UserDAO userDAO = userService.getUserDAOByUsername(user.getUsername());
            response.setUser(userDAO);
        } else {
            response.setMessage("Amount is not sufficient");
            response.setSuccess(false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addrecipient")
    public ResponseEntity<TransactionResponse> addRecipient(@Valid @RequestBody RecipientForm request) {
        TransactionResponse response = new TransactionResponse();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (recipientRepo.existsByBankNumber(request.getBankNumber())) {
            response.setMessage("Recipient has already been registered with " + request.getBankNumber());
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        Recipient recipient = new Recipient(request.getName(), request.getEmail(), request.getPhone(),
                request.getBankName(), request.getBankNumber()
        );

        recipient.setUser(user);
        accountService.saveRecipient(recipient);

        response.setMessage("Recipient successfully saved");
        response.setSuccess(true);
        UserDAO userDAO = userService.getUserDAOByUsername(user.getUsername());
        response.setUser(userDAO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
