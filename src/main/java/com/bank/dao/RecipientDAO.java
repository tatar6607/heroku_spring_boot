package com.bank.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipientDAO {

    private Long id;
    private String name;
    private String email;
    private Integer phone;
    private String bankName;
    private String bankNumber;

}
