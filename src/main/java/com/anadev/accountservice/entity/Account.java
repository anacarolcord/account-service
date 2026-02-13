package com.anadev.accountservice.entity;

import com.anadev.accountservice.entity.enums.TypeAccount;
import com.anadev.accountservice.entity.enums.TypeCurrency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccount;
    private String name;

    @Enumerated(EnumType.STRING)
    private TypeAccount typeAccount;

    @Enumerated(EnumType.STRING)
    private TypeCurrency currency;
    private BigDecimal currentBalance;
    private BigDecimal monthlyLimit;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;




}
