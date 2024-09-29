package com.dma.nn.nntask.api;

import java.math.BigDecimal;
import java.util.UUID;

public record ResponseDto (UUID id, String name, String surname, BigDecimal balanceInUSD, BigDecimal balanceInPLN) {}