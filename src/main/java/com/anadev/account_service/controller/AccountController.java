package com.anadev.account_service.controller;

import com.anadev.account_service.dto.AccountRequest;
import com.anadev.account_service.dto.AccountResponse;
import com.anadev.account_service.dto.AccountUpdateCurrencyBalance;
import com.anadev.account_service.dto.AccountUpdateMonthlyLimit;
import com.anadev.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/users/{idUser}/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public AccountResponse addNewAccount(@RequestBody AccountRequest data, @PathVariable Long idUser){
        return accountService.addNewAccount(data, idUser);
    }

    @GetMapping
    public List<AccountResponse> getAllUsersAccounts(@PathVariable Long idUser){
        return accountService.findAllAccountsFromUser(idUser);
    }

    @GetMapping("/{idAccount}")
    public AccountResponse getAccountById(@PathVariable Long idAccount){
        return accountService.findById(idAccount);
    }

    @GetMapping("/{idAccount}/limit")
    public BigDecimal getMonthlyLimit(Long userId, Long accountId){
        return accountService.getMonthlyLimit(userId,accountId);
    }

    @PatchMapping("/{idAccount}/limit")
    public AccountResponse updateMonthlyLimit (@RequestBody AccountUpdateMonthlyLimit data, @PathVariable Long idAccount, @PathVariable Long idUser){

        return accountService.updateMonthlyLimitAccount(data,idUser,idAccount);
    }

    @PatchMapping("/{idAccount}/balance")
    public AccountResponse updateCurrentBalanceAccount (@RequestBody AccountUpdateCurrencyBalance value, @PathVariable Long idAccount, @PathVariable Long idUser){

        return accountService.updateCurrentBalanceAccount(value,idUser,idAccount);
    }

    @PatchMapping("/{idAccount}/currency")
    public AccountResponse updateCurrencyAccount (@RequestBody AccountRequest data, @PathVariable Long idAccount, @PathVariable Long idUser){

        return accountService.updateCurrencyAccount(data,idUser,idAccount);
    }




}
