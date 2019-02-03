package com.pawel.task.controller;

import com.pawel.task.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@Slf4j
public class ExchangeRateController {

    @Autowired
    ExchangeRateService exchangeRateService;

    @RequestMapping(
            path = "/exchange/rate/{amount}/{currency}/{tcurrency}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )

    public ResponseEntity<BigDecimal> getExchangeRate(@PathVariable(name ="amount") Double amount, @PathVariable(name ="currency") String currency, @PathVariable(name = "tcurrency") String tcurrency){

        return exchangeRateService.getExchangeRate(amount, currency.toUpperCase(), tcurrency.toUpperCase())
                .map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());


    }

}
