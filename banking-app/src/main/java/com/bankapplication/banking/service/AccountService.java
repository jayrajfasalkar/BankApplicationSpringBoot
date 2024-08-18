package com.bankapplication.banking.service;

import com.bankapplication.banking.dto.AccountDto;
import com.bankapplication.banking.exception.AccountsException;
import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id) throws AccountsException;

    AccountDto deposit(Long id, Double amount) throws AccountsException;

    AccountDto withdraw(Long id, Double amount) throws AccountsException;

    List<AccountDto> getAllAccounts();

    void deleteAccount(Long id) throws AccountsException;

    void transferMoney(Long fromAccountId, Long toAccountId, Double amount) throws AccountsException;
}
