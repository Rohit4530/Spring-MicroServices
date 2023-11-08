package com.micro.accounts.service;

import com.micro.accounts.constants.AccountsConstants;
import com.micro.accounts.dto.AccountDto;
import com.micro.accounts.dto.CustomerDto;
import com.micro.accounts.entity.Accounts;
import com.micro.accounts.entity.Customer;
import com.micro.accounts.exceptions.CustomerAlreadyExistsException;
import com.micro.accounts.exceptions.ResourceNotFoundException;
import com.micro.accounts.mapper.AccountMapper;
import com.micro.accounts.mapper.CustomerMapper;
import com.micro.accounts.repository.AccountsRepository;
import com.micro.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService{

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer= CustomerMapper.mapToCustomer(new Customer(),customerDto);
        Optional<Customer>optionalCustomer=customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw  new CustomerAlreadyExistsException("Customer already register with given mobile number :"+customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
       Customer savedCustomer= customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
       Customer customer= customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        ()->new ResourceNotFoundException("Customer","MobileNumber",mobileNumber));
      Accounts accounts= accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(
                        ()->new ResourceNotFoundException("Customer","MobileNumber",mobileNumber));
     CustomerDto customerDto= CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
     customerDto.setAccountDto(AccountMapper.mapToAccountDto(accounts,new AccountDto()));
      return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated=false;
        AccountDto accountDto=customerDto.getAccountDto();
        if(accountDto!=null){
            Accounts accounts=accountsRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    ()->new ResourceNotFoundException("Account","AccountNumber",accountDto.getAccountNumber().toString())
            );
            AccountMapper.mapToAccounts(accountDto,accounts);
            accounts=accountsRepository.save(accounts);
            Long customerId=accounts.getCustomerId();
            Customer customer=customerRepository.findById(customerId).orElseThrow(
                    ()->new ResourceNotFoundException("Customer","CustomerId",customerId.toString())
            );
            CustomerMapper.mapToCustomer(customer,customerDto);
            customerRepository.save(customer);
            isUpdated=true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobile) {
        Customer customer =customerRepository.findByMobileNumber(mobile).orElseThrow(
                ()->new ResourceNotFoundException("Customer","MobileNumber",mobile)
                );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    private Accounts createNewAccount(Customer customer){
      Accounts newAccount=new Accounts();
      newAccount.setCustomerId(customer.getCustomerId());
      long randomAccountNumber=10000000000L+new Random().nextInt();
      newAccount.setAccountNumber(randomAccountNumber);
      newAccount.setAccountType(AccountsConstants.SAVINGS);
      newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");
      return newAccount;
    }
}
