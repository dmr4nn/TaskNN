package com.dma.nn.nntask.nbp;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Service
@Slf4j
public class CurrentExchangeRates {
    private BigDecimal USD2PLN = new BigDecimal(1);
    private BigDecimal PLN2USD = new BigDecimal(1);
    private LocalDate date = LocalDate.ofEpochDay(1);

    public boolean needUpdate() {
        return !date.isEqual(LocalDate.now());
    }

    public void update(Rate rate) {
        if (rate != null) {
            USD2PLN = rate.bid();
            PLN2USD = (new BigDecimal(1)).divide(USD2PLN,
                    2,
                    java.math.RoundingMode.HALF_UP);
            date = convert(rate.effectiveDate());
            log.info("USD2PLN:" + USD2PLN + " PLN2USD:" + PLN2USD + " date:" + date);
        }
    }

    public String convertToUSD(String amount) {
        BigDecimal value = new BigDecimal(amount);
        return value.multiply((new BigDecimal(1)).divide(USD2PLN, 4, java.math.RoundingMode.HALF_UP)).toString();
    }

    public String convertToPLN(String amount) {
        BigDecimal value = new BigDecimal(amount);
        return value.multiply(USD2PLN).toString();
    }

    public BigDecimal getUSD2PLN() {
        return USD2PLN;
    }

    public BigDecimal getPLN2USD() {
        return PLN2USD;
    }

    private LocalDate convert(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
