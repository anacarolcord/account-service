package com.anadev.account_service.controller;

import com.anadev.account_service.dto.AccountRequest;
import com.anadev.account_service.dto.AccountResponse;
import com.anadev.account_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
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

    @PatchMapping("/accounts/{idAccount}")
    public AccountResponse updateMonthlyLimit (@RequestBody AccountRequest data, @PathVariable Long idAccount, @PathVariable Long idUser){

        return accountService.updateMonthlyLimitAccount(data,idUser,idAccount);
    }

    @PatchMapping("/accounts/{idAccount}")
    public AccountResponse updateCurrentBalanceAccount (@RequestBody AccountRequest data, @PathVariable Long idAccount, @PathVariable Long idUser){

        return accountService.updateCurrentBalanceAccount(data,idUser,idAccount);
    }

    @PatchMapping("/accounts/{idAccount}")
    public AccountResponse updateCurrencyAccount (@RequestBody AccountRequest data, @PathVariable Long idAccount, @PathVariable Long idUser){

        return accountService.updateCurrencyAccount(data,idUser,idAccount);
    }




}
