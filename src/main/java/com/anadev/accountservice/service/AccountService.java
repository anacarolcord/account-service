package com.anadev.accountservice.service;

import com.anadev.accountservice.client.dto.enums.TypeTransaction;
import com.anadev.accountservice.dto.AccountRequest;
import com.anadev.accountservice.dto.AccountResponse;
import com.anadev.accountservice.dto.AccountUpdateValues;
import com.anadev.accountservice.entity.Account;
import com.anadev.accountservice.entity.User;
import com.anadev.accountservice.entity.enums.TypeAccount;
import com.anadev.accountservice.exepcions.AccountNottFoundException;
import com.anadev.accountservice.exepcions.UserNotFoundException;
import com.anadev.accountservice.messaging.WarningLimitProducer;
import com.anadev.accountservice.repository.AccountRepository;
import com.anadev.accountservice.repository.UserRepository;
import com.anadev.accountservice.service.strategy.TransactionStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ApplicationContext context;//conteiner do spring que guarda todos os beans
    private final WarningLimitProducer producerRabbit;

    @Transactional
    public AccountResponse addNewAccount (AccountRequest data, Long idUser){
        User user = getUser(idUser);

        Account account = new Account();
        account.setName(data.name());
        account.setCurrency(data.currency());
        account.setCurrentBalance(data.currentBalance());
        account.setTypeAccount(data.typeAccount());
        account.setMonthlyLimit(data.monthlyLimit());

        accountRepository.save(account);
        user.addAccount(account); //correção

        return AccountResponse.fromEntity(account);
    }

    public AccountResponse findById(Long idAccount){
        Account account = accountRepository.findById(idAccount)
                .orElseThrow(AccountNottFoundException::new);

        return AccountResponse.fromEntity(account);
    }


    public List<AccountResponse> findAllAccountsFromUser(Long id){
        User user = getUser(id);

        List<Account> accounts = user.getAccounts();

        return accounts.stream().map(AccountResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountResponse updateValues(AccountUpdateValues data, Long idAccount){
        Account account = accountRepository.findById(idAccount)
                .orElseThrow(AccountNottFoundException::new);

        TypeAccount typeAccount = account.getTypeAccount();

        AccountResponse response;

        if (typeAccount.equals(TypeAccount.CARTAO_CREDITO) ) {
            response = updateMonthlyLimitAccount(data,account);
        }else{
            response = updateCurrentBalanceAccount(data,account);
        }

        return response;
    }

    @Transactional
    public AccountResponse updateMonthlyLimitAccount(AccountUpdateValues data, Account accountUser) {

        BigDecimal valorCompra = data.value();
        BigDecimal limiteAtual = accountUser.getMonthlyLimit();
        BigDecimal faturaAtual = accountUser.getCurrentBalance();
        BigDecimal saldoPosTransacao;
        BigDecimal limitePosTransacao = limiteAtual.subtract(valorCompra);

        //se atransacao for de entrada
        if (data.typeTransaction().equals(TypeTransaction.ENTRADA)) {
            //processa o pagamento da fatura atual
            saldoPosTransacao = processCreditCardPayment(data, accountUser);


        }else {

            if (faturaAtual.add(valorCompra).compareTo(limiteAtual) > 0) {
                throw new IllegalArgumentException("Operação proibida, limite insuficiente para a compra! Limite atual R$" + limiteAtual);
            }

            if (limitePosTransacao.compareTo(BigDecimal.ZERO)< 0){
                throw new IllegalArgumentException("Operação cancelada, o limite ficaria nulo");
            }else{

                limitePosTransacao = limiteAtual.subtract(valorCompra);
                saldoPosTransacao = faturaAtual.add(valorCompra);


            }

        }

        accountUser.setMonthlyLimit(limitePosTransacao);
        accountUser.setCurrentBalance(saldoPosTransacao);

        accountRepository.save(accountUser);
        return AccountResponse.fromEntity(accountUser);
    }

    @Transactional
    public AccountResponse updateCurrentBalanceAccount(AccountUpdateValues data, Account accountUser){

        TransactionStrategy strategy = context.getBean(data.typeTransaction().getStrategyBeanName(), TransactionStrategy.class);

        AccountResponse response = strategy.execute(accountUser, data.value());

        accountRepository.save(accountUser);

        return response;
    }

    @Transactional
    public User getUser(Long idUser){
        return userRepository.findById(idUser)
                .orElseThrow(UserNotFoundException::new);
    }
    
    public BigDecimal getMonthlyLimit(Long idAccount){
        Account accountUser = accountRepository.findById(idAccount)
                .orElseThrow(AccountNottFoundException::new);

        return accountUser.getMonthlyLimit();
    }

    public BigDecimal processCreditCardPayment(AccountUpdateValues data, Account accountUser){

        BigDecimal currentBalance = accountUser.getCurrentBalance();
        BigDecimal valorPagamento = data.value();
        BigDecimal saldoPosPagamento = currentBalance.subtract(valorPagamento);
        BigDecimal limiteAtual = accountUser.getMonthlyLimit();
        BigDecimal limitePosPagamento;

        //se o valor do pagamento for maior que o saldo atual
        if(valorPagamento.compareTo(currentBalance) > 0){

            throw new IllegalArgumentException("Valor é acima do valor da fatura atual de R$" + currentBalance);

        } else if ((valorPagamento.compareTo(BigDecimal.ZERO) <= 0) || (valorPagamento.compareTo(currentBalance) != 0)){

            throw new IllegalArgumentException("Operação proibida");

        }else if (valorPagamento.compareTo(currentBalance) == 0){

             saldoPosPagamento = currentBalance.subtract(valorPagamento);
             limitePosPagamento = limiteAtual.add(valorPagamento);
             accountUser.setMonthlyLimit(limitePosPagamento);
        }

        return saldoPosPagamento;
    }




}