package com.bankapplication.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//@Data
//@AllArgsConstructor
//public class AccountDto { //to transfer the data between client and server
//    private Long id;
//    private String accountHolderName;
//    private double balance;
//}

public record AccountDto(Long id,
                         String accountHolderName,
                         double balance) {
}