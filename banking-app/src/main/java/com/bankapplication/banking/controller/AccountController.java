package com.bankapplication.banking.controller;

import com.bankapplication.banking.dto.AccountDto;
import com.bankapplication.banking.entity.Account;
import com.bankapplication.banking.exception.AccountsException;
import com.bankapplication.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //Add account REST API
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    //Get account by ID REST API
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) throws AccountsException {
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    //Deposit money in account REST API
    //why this is not working?
    //getting confused which REST api to use
//    @PutMapping("/{id}/{amount}")
//    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @PathVariable double amount) {
//        AccountDto accountDto = accountService.deposit(id,amount);
//        return ResponseEntity.ok(accountDto);
//    }
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id,
                                              @RequestBody Map<String, Double> request) throws AccountsException {
        AccountDto accountDto = accountService.deposit(id,request.get("amount"));
        return ResponseEntity.ok(accountDto);
    }

    //Withdraw money from account REST API
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,
                                               @RequestBody Map<String, Double> request) throws AccountsException {
        AccountDto accountDto = accountService.withdraw(id,request.get("amount"));
        return ResponseEntity.ok(accountDto);
    }


    //Get all accounts REST API
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    //Delete Account REST API
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) throws AccountsException {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted successfully");
    }

    @PutMapping("/{fromAccountId}/{toAccountId}/{amount}")
    public ResponseEntity<String> transferMoney(@PathVariable Long fromAccountId,
                                                @PathVariable Long toAccountId,
                                                @PathVariable double amount) throws AccountsException {
        accountService.transferMoney(fromAccountId, toAccountId, amount);
        return ResponseEntity.ok("Transfer successful");

    }
}
























