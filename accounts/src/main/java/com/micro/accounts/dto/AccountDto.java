package com.micro.accounts.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AccountDto {
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
}
