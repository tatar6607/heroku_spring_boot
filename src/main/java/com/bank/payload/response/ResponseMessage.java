package com.bank.payload.response;

import lombok.Data;

@Data
public class ResponseMessage {

    boolean isSuccess;
    String message;

}
