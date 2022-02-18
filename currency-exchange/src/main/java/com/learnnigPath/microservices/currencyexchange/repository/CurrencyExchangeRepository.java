package com.learnnigPath.microservices.currencyexchange.repository;

import com.learnnigPath.microservices.currencyexchange.bean.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange,Long> {

        public CurrencyExchange findByFromAndTo(String from,String to);
}
