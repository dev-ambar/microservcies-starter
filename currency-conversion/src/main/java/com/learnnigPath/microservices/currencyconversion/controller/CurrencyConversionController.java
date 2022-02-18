package com.learnnigPath.microservices.currencyconversion.controller;

import com.learnnigPath.microservices.currencyconversion.bean.CurrencyConversion;
import com.learnnigPath.microservices.currencyconversion.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from , @PathVariable String to , @PathVariable BigDecimal quantity)
    {
         String URL = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
        HashMap<String,String> pathVariable = new HashMap<>();
        pathVariable.put("from",from);
        pathVariable.put("to",to);
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(URL, CurrencyConversion.class, pathVariable);
        CurrencyConversion conversion = responseEntity.getBody();
         BigDecimal totalAmt = quantity.multiply(conversion.getConversionMultiple());
        return  new CurrencyConversion(1001L,from,to,quantity,
                conversion.getConversionMultiple(),totalAmt,conversion.getEnvironment()+ " RestTemplate");
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from , @PathVariable String to , @PathVariable BigDecimal quantity)
    {
        CurrencyConversion conversion = proxy.getExchangeData(from,to);
        BigDecimal totalAmt = quantity.multiply(conversion.getConversionMultiple());
        return  new CurrencyConversion(1001L,from,to,quantity,
                conversion.getConversionMultiple(),totalAmt,conversion.getEnvironment()+" OpenFeignClient");
    }
}
