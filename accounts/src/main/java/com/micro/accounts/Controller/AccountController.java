package com.micro.accounts.Controller;

import com.micro.accounts.constants.AccountsConstants;
import com.micro.accounts.dto.CustomerDto;
import com.micro.accounts.dto.payload.ResponseDto;
import com.micro.accounts.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api",produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountController {
    @Autowired
    private IAccountService iAccountService;
    @PostMapping("/create")
    public ResponseEntity<ResponseDto>createAccount(@RequestBody CustomerDto customerDto){
        iAccountService.createAccount(customerDto);
        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201,
                        AccountsConstants.MESSAGE_201));
    }
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto>createAccount(@RequestParam String mobileNumber){
        CustomerDto customerDto=iAccountService.fetchAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseDto>updateAccountDetails(@RequestBody CustomerDto customerDto){
        boolean isUpdated=iAccountService.updateAccount(customerDto);
        if(isUpdated){
            return  ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(
                            AccountsConstants.STATUS_200,
                            AccountsConstants.MESSAGE_200
                    ));

        }else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(
                            AccountsConstants.STATUS_500,
                            AccountsConstants.MESSAGE_500
                    ));
        }
    }
    @DeleteMapping("/delete")
     public ResponseEntity<ResponseDto>deleteAccountDetails(@RequestParam String mobileNumber ){
        boolean isDeleted=iAccountService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(
                            AccountsConstants.STATUS_200,
                            AccountsConstants.MESSAGE_200
                    ));
        }else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(
                            AccountsConstants.STATUS_500,
                            AccountsConstants.MESSAGE_500
                    ));
        }
     }
}
