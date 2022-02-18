package com.learnnigPath.microservices.currencyexchange.service;

import com.learnnigPath.microservices.currencyexchange.repository.CurrencyExchangeRepository;
import com.learnnigPath.microservices.currencyexchange.bean.CurrencyExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyExchangeService {
    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;
    public CurrencyExchange retrieveCurrencyExchangeValue(String from ,String to)
    {
        return currencyExchangeRepository.findByFromAndTo(from,to);
    }
}
