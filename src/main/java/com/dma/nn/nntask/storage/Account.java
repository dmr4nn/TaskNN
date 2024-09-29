package com.dma.nn.nntask.storage;

import com.dma.nn.nntask.api.AccountDto;
import com.dma.nn.nntask.api.ResponseDto;
import com.dma.nn.nntask.nbp.CurrentExchangeRates;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String name;
    String surname;
    BigDecimal balance;

    public Account() {}

    public void setId(UUID id) {
        this.id = id;
    }

    @jakarta.persistence.Id
    public UUID getId() {
        return id;
    }

    public Account(AccountDto accountDto) {
        id = UUID.randomUUID();
        name = accountDto.name();
        surname = accountDto.surname();
        balance = new BigDecimal(accountDto.balance());
    }

    public ResponseDto fullInfo(CurrentExchangeRates currentExchangeRates) {
        return new ResponseDto(id, name, surname, balance, balance.multiply(currentExchangeRates.getUSD2PLN()));
    }
}
