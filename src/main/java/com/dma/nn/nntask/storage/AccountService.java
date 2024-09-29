package com.dma.nn.nntask.storage;

import com.dma.nn.nntask.api.AccountDto;
import com.dma.nn.nntask.api.ResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public interface AccountService {
    public UUID create(AccountDto accountDto, boolean convertPLN2USD);
    public BigDecimal updateAccountBalance(UUID uuid, BigDecimal amount);
    public ResponseDto getAccount(UUID uuid);
    public List<Account> listAccounts();
}
