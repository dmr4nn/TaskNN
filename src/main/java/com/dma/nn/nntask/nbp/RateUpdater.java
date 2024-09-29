package com.dma.nn.nntask.nbp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class RateUpdater {

    CurrentExchangeRates currentExchangeRates;

    public RateUpdater(CurrentExchangeRates currentExchangeRates) {
        this.currentExchangeRates = currentExchangeRates;
    }

    private final static String api = "https://api.nbp.pl/api/exchangerates/rates/c/usd?format=json";

    @Scheduled (fixedDelay = 60*1000)  //(cron = "* 45/5 11,12 * * *")
    public void httpGetRequest() throws IOException, InterruptedException {
        if (!currentExchangeRates.needUpdate()) return;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create(api))
                .headers("Accept-Enconding", "gzip, deflate")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            ExchangeRate exchangeRate = mapper.readValue(response.body(), ExchangeRate.class);
            currentExchangeRates.update(exchangeRate.rates().get(0));
        }
    }
}
