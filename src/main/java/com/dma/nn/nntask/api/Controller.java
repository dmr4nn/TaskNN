package com.dma.nn.nntask.api;

import com.dma.nn.nntask.nbp.CurrentExchangeRates;
import com.dma.nn.nntask.storage.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
//@RequestMapping("/")
public class Controller {

    AccountService accountService;
    CurrentExchangeRates currentExchangeRates;

    public Controller(@Autowired AccountService accountService, CurrentExchangeRates currentExchangeRates) {
        this.accountService = accountService;
        this.currentExchangeRates = currentExchangeRates;
    }

    @PostMapping(value="/create", consumes = "application/json", produces="application/json")
    public ResponseEntity<?> createAccount(@RequestBody AccountDto account) {
        UUID id = accountService.create(account, true);
        return new ResponseEntity<>(new ApiResponse("ok", id.toString()), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(produces="application/json")
    public ResponseEntity<?> getAccounts() {
        return new ResponseEntity<>(accountService.listAccounts(), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value="/{id}", produces="application/json")
    public ResponseEntity<?> getAccount(@PathVariable(value = "id") UUID id) {
        return new ResponseEntity<>(accountService.getAccount(id), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value="/convert/pln2usd", consumes="application/json", produces="application/json")
    public ResponseEntity<?> convertPLN2USD(String amount) {
        return new ResponseEntity<>(new ApiResponse("USD", currentExchangeRates.convertToUSD(amount)), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value="/convert/usd2pln", consumes="application/json", produces="application/json")
    public ResponseEntity<?> convertUSD2PLN(String amount) {
        return new ResponseEntity<>(new ApiResponse("USD", currentExchangeRates.convertToPLN(amount)), new HttpHeaders(), HttpStatus.OK);
    }
}
