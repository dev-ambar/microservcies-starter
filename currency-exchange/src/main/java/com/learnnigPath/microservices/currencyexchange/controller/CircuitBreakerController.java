package com.learnnigPath.microservices.currencyexchange.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
    @GetMapping("/sample-api")
    //@Retry(name = "default") this is for default behaviour
    //@Retry(name = "sample-api",fallbackMethod = "hardCodedResponse")
    //@CircuitBreaker(name="default",fallbackMethod="hardCodedResponse" )
    //@RateLimiter(name="default")
    @Bulkhead(name="sample-api")
    public String sampleApi()
    {
        logger.info("SampleApi is calling");
        /*ResponseEntity<String> forEntity = new RestTemplate().getForEntity
                ("http://localhost:8080/dummy-api", String.class);*//*
        return forEntity.getBody();*/
        return "sampleAPI calling";
    }

    private String hardCodedResponse(Exception e)
    {
        logger.info("exception which ocer >{}",e.getMessage());
        return "fallbackResponse";
    }
}
