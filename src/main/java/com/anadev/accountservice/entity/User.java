package com.anadev.accountservice.entity;

import com.anadev.accountservice.entity.enums.TypeUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();

    public Long getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public TypeUser getTypeUser() {
        return typeUser;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public void addAccount(Account account){
        this.accounts.add(account);
        account.setUser(this);
    }

    //unica forma de adicionar uma nova conta na lista do usuario é chamando esse metodo addAcount
    //o getAccounts retornando uma lista imodifcavel proteje a classe para que nao seja possivel modiicar a lista !!
}
