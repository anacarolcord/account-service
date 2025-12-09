package com.anadev.account_service.entity;

import com.anadev.account_service.entity.enums.TypeAccount;
import com.anadev.account_service.entity.enums.TypeCurrency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAccount;
    private String name;
    private TypeAccount typeAccount;
    private TypeCurrency currency;
    private DecimalFormat currentBalance;
    private DecimalFormat monthlyLimit;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;




}
