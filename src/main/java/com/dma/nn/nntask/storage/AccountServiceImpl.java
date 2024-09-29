package com.dma.nn.nntask.storage;

import com.dma.nn.nntask.api.AccountDto;
import com.dma.nn.nntask.api.ResponseDto;
import com.dma.nn.nntask.nbp.CurrentExchangeRates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final CurrentExchangeRates currentExchangeRates;

    public AccountServiceImpl(@Autowired AccountRepository accountRepository, @Autowired CurrentExchangeRates currentExchangeRates) {
        this.accountRepository = accountRepository;
        this.currentExchangeRates = currentExchangeRates;
    }

    public UUID create(AccountDto accountDto, boolean convertPLN2USD) {
        if (accountDto == null || accountDto.name() == null || accountDto.name().isBlank()
                || accountDto.surname() == null || accountDto.surname().isBlank()) throw new IllegalArgumentException("Niewystarczające dane do utworzenia konta");
        Account account = new Account(accountDto);
        if (convertPLN2USD) {
            account.setBalance(account.getBalance().multiply(currentExchangeRates.getPLN2USD()));
        }
        accountRepository.save(account);
        return account.getId();
    }

    public BigDecimal updateAccountBalance(UUID uuid, BigDecimal amount) {
        Optional<Account> accountOpt = accountRepository.findById(uuid);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            account.setBalance(account.getBalance().add(amount));
            accountRepository.save(account);
            return account.getBalance();
        }
        throw new IllegalArgumentException("Konto o podanym identyfikatorze nie istnieje");
    }

    public ResponseDto getAccount(UUID uuid) {
        Optional<Account> accountOpt = accountRepository.findById(uuid);
        if (accountOpt.isPresent()) {
            return accountOpt.get().fullInfo(currentExchangeRates);
        }
        throw new IllegalArgumentException("Konto o podanym identyfikatorze nie istnieje");
    }

    public List<Account> listAccounts() {
        return accountRepository.findAll();
    }
}
