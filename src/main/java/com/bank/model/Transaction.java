package com.bank.model;

import com.bank.dao.RecipientDAO;
import com.bank.dao.TransactionDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String description;
    private String type;
    private Double amount;
    private BigDecimal availableBalance;
    private Boolean isTransfer;

    public Transaction(Date date, String description, String type, Double amount, BigDecimal availableBalance, Boolean isTransfer, Account account) {
        this.date = date;
        this.description = description;
        this.type = type;
        this.amount = amount;
        this.availableBalance = availableBalance;
        this.isTransfer = isTransfer;
        this.account = account;
    }

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
