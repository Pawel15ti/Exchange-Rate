package com.pawel.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateC {

    private String table;
    private String currency;
    private String code;
    private RatesC[] rates;

}
