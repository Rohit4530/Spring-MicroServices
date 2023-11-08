package com.micro.accounts.mapper;

import com.micro.accounts.dto.AccountDto;
import com.micro.accounts.entity.Accounts;

public class AccountMapper {
    public static AccountDto mapToAccountDto(Accounts accounts,AccountDto accountDto){
        accountDto.setAccountNumber(accounts.getAccountNumber());
        accountDto.setAccountType(accounts.getAccountType());
        accountDto.setBranchAddress(accounts.getBranchAddress());
        return accountDto;
    }
    public static Accounts mapToAccounts(AccountDto accountDto,Accounts accounts){
        accounts.setAccountNumber(accountDto.getAccountNumber());
        accounts.setAccountType(accountDto.getAccountType());
        accounts.setBranchAddress(accountDto.getBranchAddress());
        return  accounts;
    }


}
