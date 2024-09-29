package com.dma.nn.nntask.api;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ApiResponse (String msg, @JsonInclude(JsonInclude.Include.NON_NULL) String value) {}

