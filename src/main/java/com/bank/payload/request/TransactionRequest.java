package com.bank.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TransactionRequest {
    @NotNull(message = "Amaount can not be blank")
    private Double amount;

    @NotNull(message = "description can not be null")
    private String description;
}
