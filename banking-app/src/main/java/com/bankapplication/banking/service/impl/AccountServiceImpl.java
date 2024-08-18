package com.bankapplication.banking.service.impl;

import com.bankapplication.banking.dto.AccountDto;
import com.bankapplication.banking.entity.Account;
import com.bankapplication.banking.entity.Transaction;
import com.bankapplication.banking.exception.AccountsException;
import com.bankapplication.banking.mapper.AccountMapper;
import com.bankapplication.banking.repository.AccountRepository;
import com.bankapplication.banking.repository.TransactionRepository;
import com.bankapplication.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private TransactionRepository transactionRepository;
    //@Autowired
    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public AccountDto createAccount(AccountDto accountDto){
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    public AccountDto getAccountById(Long id) throws AccountsException {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountsException("Account not found"));
        return AccountMapper.mapToAccountDto(account);
    }

    public AccountDto deposit(Long id, Double amount) throws AccountsException {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountsException("Account not found"));
        account.setBalance(account.getBalance() + amount);
        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setAccountId(id);
        transaction.setTransactionType("DEPOSIT");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    public AccountDto withdraw(Long id, Double amount) throws AccountsException {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountsException("Account not found"));
        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);
        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setAccountId(id);
        transaction.setTransactionType("WITHDRAW");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    public List<AccountDto> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        List<AccountDto> accountDtos = new ArrayList<>();
        for(Account account : accounts){
            AccountDto accountDto = AccountMapper.mapToAccountDto(account);
            accountDtos.add(accountDto);
        }
        return accountDtos;

        //return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.yoList());
    }

    @Override
    public void deleteAccount(Long id) throws AccountsException {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountsException("Account not found"));
        accountRepository.deleteById(id);
    }

    @Override
    public void transferMoney(Long fromAccountId, Long toAccountId, Double amount) throws AccountsException {
        Account account1 = accountRepository.findById(fromAccountId).orElseThrow(() -> new AccountsException("Account not found"));
        Account account2 = accountRepository.findById(toAccountId).orElseThrow(() -> new AccountsException("Account not found"));
        account1.setBalance(account1.getBalance() - amount);
        account2.setBalance(account2.getBalance() + amount);
        Account savedAccount1 = accountRepository.save(account1);
        Account savedAccount2 = accountRepository.save(account2);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setAccountId(fromAccountId);
        transaction.setTransactionType("TRANSFER-WITHDRAW");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(amount);
        transaction2.setAccountId(toAccountId);
        transaction2.setTransactionType("TRANSFER-DEPOSIT");
        transaction2.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction2);
    }
}



























