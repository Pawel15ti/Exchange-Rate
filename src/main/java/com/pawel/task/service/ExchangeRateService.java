package com.pawel.task.service;

import com.google.gson.Gson;
import com.pawel.task.dto.ExchangeRateA;
import com.pawel.task.dto.RatesA;
import com.pawel.task.exception.ApiExchangeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExchangeRateService {
    final List<RatesA> ratesAList = run1();
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public Optional<BigDecimal> getExchangeRate(Double amount, String currency, String tcurrency) {
        List<ApiExchangeException> listException;
        try {
            log.info("start getExchangeRate");

            RatesA currencyRate = ratesAList.stream().filter(s -> s.getCode().equals(currency)).findFirst().orElse(null);
            RatesA targetCurrencyRate = ratesAList.stream().filter(s -> s.getCode().equals(tcurrency)).findFirst().orElse(null);

            if (currencyRate == null || targetCurrencyRate == null) {
                return Optional.empty();
            }

            log.info("end getExchangeRate");
            
            return convertToExchangeRate(amount, currencyRate, targetCurrencyRate);


        } catch (ApiExchangeException a) {
            log.error("Error ApiExchangeException: " + a);
            throw a;

        } catch (Exception e) {
            throw new ApiExchangeException(500, "code-500", "internal error");
        }

    }

    private Optional<BigDecimal> convertToExchangeRate(Double amount, RatesA myCurrencyRate, RatesA targetCurrencyRate) {

        BigDecimal bigDecimal = new BigDecimal(myCurrencyRate.getMid()).multiply(new BigDecimal(amount)).divide(new BigDecimal(targetCurrencyRate.getMid()), 4, RoundingMode.HALF_DOWN);

        return Optional.of(bigDecimal);

    }

    @Scheduled(fixedRate = 86_400_000)
    private List<RatesA> run1() {
        RestTemplate restTemplate = new RestTemplate();
        String urlNbp = "http://api.nbp.pl/api/exchangerates/tables/A/";
        ResponseEntity<ExchangeRateA[]> forEntity = restTemplate.getForEntity(urlNbp, ExchangeRateA[].class);
        ExchangeRateA[] body = forEntity.getBody();
        List<ExchangeRateA> ratesAS = Arrays.asList(body);


        List<RatesA> resultList = convert(ratesAS.get(0).getRates());


        for (RatesA ss : resultList)
            System.out.println(ss);

        return resultList;
    }

    private List<RatesA> convert(RatesA[] rates) {

        return Arrays.stream(rates).map(s -> convertRates(s)).collect(Collectors.toList());
    }

    private RatesA convertRates(RatesA s) {
        return RatesA.builder()
                .code(s.getCode())
                .currency(s.getCurrency())
                .mid(s.getMid())
                .build();
    }


}
