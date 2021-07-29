package com.bank.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class LoginForm {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    private String password;

}
