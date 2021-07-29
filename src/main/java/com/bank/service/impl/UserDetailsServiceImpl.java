package com.bank.service.impl;

import com.bank.dao.RecipientDAO;
import com.bank.dao.TransactionDAO;
import com.bank.dao.UserDAO;
import com.bank.model.Account;
import com.bank.model.Recipient;
import com.bank.model.Transaction;
import com.bank.model.User;
import com.bank.repository.AccountRepo;
import com.bank.repository.UserRepo;
import com.bank.service.TransactionService;
import com.bank.service.UserService;
import com.bank.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    TransactionService transactionService;


    @Override
    public UserDAO getUserDAO(User user) {
        UserDAO userDAO = new UserDAO();
        userDAO.setUserId(user.getUserId());
        userDAO.setUsername(user.getUsername());
        userDAO.setPassword(user.getPassword());
        userDAO.setFirstName(user.getFirstName());
        userDAO.setLastName(user.getLastName());
        userDAO.setEmail(user.getEmail());
        Boolean isAdmin = user.getUserRoles().stream().filter(role -> role.getRole().getName().equals("ROLE_ADMIN")).findAny().isPresent();
        userDAO.setIsAdmin(isAdmin);
        if(user.getAccount() != null) {
            userDAO.setAccountNumber(user.getAccount().getAccountNumber());
            userDAO.setAccountBalance(user.getAccount().getAccountBalance());
        }
        assert user.getAccount() != null;
//      List<TransactionDAO> transactions = user.getAccount().getTransactions().stream().map(this::getTransactionDAO).collect(Collectors.toList());


        List<Transaction> actualTransactionList  = transactionService.getAllTransaction(user.getAccount().getId());
        List<TransactionDAO> transactions = actualTransactionList.stream().map(this::getTransactionDAO).collect(Collectors.toList());
        userDAO.setTransactions(transactions);

        List<RecipientDAO> recipients = user.getRecipient().stream().map(this::getRecipientDAO).collect(Collectors.toList());
        userDAO.setRecipients(recipients);

        return userDAO;
    }

    private RecipientDAO getRecipientDAO(Recipient recipient) {
        RecipientDAO recipientDAO = new RecipientDAO();
        recipientDAO.setId(recipient.getId());
        recipientDAO.setName(recipient.getName());
        recipientDAO.setEmail(recipient.getEmail());
        recipientDAO.setPhone(recipient.getPhone());
        recipientDAO.setBankName(recipient.getBankName());
        recipientDAO.setBankNumber(recipient.getBankNumber());
        return recipientDAO;
    }

    private TransactionDAO getTransactionDAO(Transaction transaction) {
        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.setId(transaction.getId());
        transactionDAO.setDate(DateUtil.getDateAsString(transaction.getDate(), DateUtil.SIMPLE_DATE_FORMAT));
        transactionDAO.setTime(DateUtil.getDateAsString(transaction.getDate(), DateUtil.SIMPLE_DATE_TIME_FORMAT));
        transactionDAO.setDescription(transaction.getDescription());
        transactionDAO.setType(transaction.getType());
        transactionDAO.setAmount(transaction.getAmount());
        transactionDAO.setAvailableBalance(transaction.getAvailableBalance());
        transactionDAO.setIsTransfer(transaction.getIsTransfer());

        return transactionDAO;
    }


//    public UserDAO transformUser(User user) {
//        UserDAO userDAO = new UserDAO();
//        userDAO.setUserId(user.getUserId());
//        userDAO.setUsername(user.getUsername());
//        userDAO.setPassword(user.getPassword());
//        userDAO.setFirstName(user.getFirstName());
//        userDAO.setLastName(user.getLastName());
//        userDAO.setEmail(user.getEmail());
//        return userDAO;
//    }

    @Override
    public UserDAO getUserDAOByUsername(String username) {
//        User user = userRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Error: user not found. " + username));
       Optional<User> user = userRepo.findByUsername(username);
       UserDAO userDAO =  null;
       if(user.isPresent()) {
           userDAO = getUserDAO(user.get());
       }
       return userDAO;
    }

    // bu kisim isAdmin olmadan yapildi
    @Override
    public List<UserDAO> getAllUsers() {
        List<User> users = (List<User>) userRepo.findAll();
        return users.stream().map(this::getUserDAO).collect(Collectors.toList());

    }

    @Override
    public List<RecipientDAO> getRecipients(String username) {
        List<RecipientDAO> recipients = null;
        Optional <User> userOptional = userRepo.findByUsername(username);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            recipients = user.getRecipient().stream().map(this::getRecipientDAO).
                    collect(Collectors.toList());
        }
        return recipients;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.
                findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("Error : Username not found " + username));

        return user;
    }
}
