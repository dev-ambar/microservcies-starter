package com.learnnigPath.microservices.currencyexchange.controller;

import com.learnnigPath.microservices.currencyexchange.bean.CurrencyExchange;
import com.learnnigPath.microservices.currencyexchange.service.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;
    @Autowired
    private CurrencyExchangeService currencyExchangeService;
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange getExchangeData(@PathVariable String from , @PathVariable String to)
    {
        CurrencyExchange currencyExchange = currencyExchangeService.retrieveCurrencyExchangeValue(from,to);
         if(currencyExchange == null)
         {
              throw new RuntimeException("unable to find the data for :"+from +" And to :"+to);
         }
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
