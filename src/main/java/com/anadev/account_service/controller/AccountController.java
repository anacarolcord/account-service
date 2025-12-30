package com.anadev.account_service.controller;

import com.anadev.account_service.dto.AccountRequest;
import com.anadev.account_service.dto.AccountResponse;
import com.anadev.account_service.dto.AccountUpdateValues;
import com.anadev.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/users/{idUser}/accounts")
    public AccountResponse addNewAccount(@RequestBody AccountRequest data, @PathVariable Long idUser){
        return accountService.addNewAccount(data, idUser);
    }

    @GetMapping("/users/{idUser}/accounts")
    public List<AccountResponse> getAllUsersAccounts(@PathVariable Long idUser){
        return accountService.findAllAccountsFromUser(idUser);
    }

    @GetMapping("accounts/{idAccount}")
    public AccountResponse getAccountById(@PathVariable Long idAccount){
        return accountService.findById(idAccount);
    }

    @GetMapping("accounts/{idAccount}/limit")
    public BigDecimal getMonthlyLimit(Long accountId){
        return accountService.getMonthlyLimit(accountId);
    }

    @PatchMapping("accounts/{idAccount}")
    public AccountResponse updateBalanceOrMonthlyLimit (@RequestBody AccountUpdateValues data, @PathVariable Long idAccount){

        return accountService.updateValues(data,idAccount);
    }






}
