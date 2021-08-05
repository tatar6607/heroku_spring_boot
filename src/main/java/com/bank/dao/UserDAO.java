package com.bank.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDAO {

    private Long userId;
    private String username;

    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isAdmin;
    private Long accountNumber;
    private BigDecimal accountBalance;
    private List<TransactionDAO> transactions;
    private List<RecipientDAO> recipients;

    private List<TransactionDAO> oneWeekTransactions;
    private List<RecipientDAO> oneWeekRecipients;

    private Long totalUsers;
    private Double totalBalance;
}
