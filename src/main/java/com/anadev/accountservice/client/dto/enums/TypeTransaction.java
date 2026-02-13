package com.anadev.accountservice.client.dto.enums;

import lombok.Getter;

@Getter
public enum TypeTransaction {
    ENTRADA ("entradaStrategy"),
    SAIDA ("saidaStrategy");

    private final String strategyBeanName;

    TypeTransaction(String strategyBeanName){
        this.strategyBeanName = strategyBeanName;
    }

}

