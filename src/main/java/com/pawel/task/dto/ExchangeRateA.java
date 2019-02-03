package com.pawel.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateA {

    private String table;
    private String no;
    private Date effectiveDate;
    private RatesA[] rates;

}
