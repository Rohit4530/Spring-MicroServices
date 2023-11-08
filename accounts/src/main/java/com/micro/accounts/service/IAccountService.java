package com.micro.accounts.service;

import com.micro.accounts.dto.CustomerDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {
    public  void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);
    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobile);
}
