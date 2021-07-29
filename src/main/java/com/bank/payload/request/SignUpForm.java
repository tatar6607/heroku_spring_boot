package com.bank.payload.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;


@Data
public class SignUpForm {
    @NotBlank
    @Size(max = 50)
    private String firstname;

    @NotBlank
    @Size(max = 50)
    private String lastname;

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    private String password;

    @NotBlank
    @Email(message = "Please enter a valid email")
    private String email;

    private Set<String> roles;




}
