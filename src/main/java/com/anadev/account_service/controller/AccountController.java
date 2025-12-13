package com.anadev.account_service.controller;

import com.anadev.account_service.dto.AccountRequest;
import com.anadev.account_service.dto.AccountResponse;
import com.anadev.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{idAccount}/limit")
    public AccountResponse updateMonthlyLimit (@RequestBody AccountRequest data, @PathVariable Long idAccount, @PathVariable Long idUser){

        return accountService.updateMonthlyLimitAccount(data,idUser,idAccount);
    }

    @PatchMapping("/{idAccount}/balance")
    public AccountResponse updateCurrentBalanceAccount (@RequestBody AccountRequest data, @PathVariable Long idAccount, @PathVariable Long idUser){

        return accountService.updateCurrentBalanceAccount(data,idUser,idAccount);
    }

    @PatchMapping("/{idAccount}/currency")
    public AccountResponse updateCurrencyAccount (@RequestBody AccountRequest data, @PathVariable Long idAccount, @PathVariable Long idUser){

        return accountService.updateCurrencyAccount(data,idUser,idAccount);
    }




}
