package com.bank.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor

public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountNumber;

    private BigDecimal accountBalance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Transaction> transactions;

    public Account(Long accountNumber, BigDecimal accountBalance, List<Transaction> transactions) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.transactions = transactions;
    }
}
