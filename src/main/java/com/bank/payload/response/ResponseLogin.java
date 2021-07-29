package com.bank.payload.response;

import com.bank.dao.UserDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLogin {
    private UserDAO user;
    String jwt;

}
