package com.bank.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDAO {
    private Long id;
    private String date;
    private String time;
    private String description;
    private String type;
    private Double amount;
    private BigDecimal availableBalance;
    private Boolean isTransfer;



}
