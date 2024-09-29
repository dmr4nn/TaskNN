package com.dma.nn.nntask.nbp;

import java.util.List;

public record ExchangeRate(String table, String currency, String code, List<Rate> rates) {}
