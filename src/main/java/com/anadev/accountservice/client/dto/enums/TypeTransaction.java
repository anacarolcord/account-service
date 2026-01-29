package com.anadev.accountservice.client.dto.enums;

public enum TypeTransaction {
    ENTRADA ("entradaStrategy"),
    SAIDA ("saidaStrategy");

    private final String strategyBeanName;

    TypeTransaction(String strategyBeanName){
        this.strategyBeanName = strategyBeanName;
    }

    public String getStrategyBeanName(){
        return strategyBeanName;
    }
}

