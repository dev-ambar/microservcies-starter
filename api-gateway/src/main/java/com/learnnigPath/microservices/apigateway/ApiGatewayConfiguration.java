package com.learnnigPath.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator  gatewayRouter(RouteLocatorBuilder routeLocatorBuilder)
    {
        return routeLocatorBuilder.routes()
                .route(p -> p.path("/get")
                        .filters(f ->
                                f.addRequestHeader("MyHeader","MyURI")
                                        .addRequestParameter("MyRequestParam","MyRequestParamValue")
                        )
                        .uri("http://httpbin.org:80")).
                        route(p->p.path("/currency-exchange/**")
                                .uri("lb://currency-exchange")).
                        route(p->p.path("/currency-conversion/**").
                                uri("lb://currency-conversion")).
                        route(p->p.path("/currency-conversion-feign/**").
                                uri("lb://currency-conversion")).
                        route(p->p.path("/ccf/**")
                                .filters(f->
                                        f.rewritePath("/ccf/(?<segment>.*)",
                                                "/currency-conversion-feign/${segment}")).
                                        uri("lb://currency-conversion")).
                        route(p->p.path("/ccr/**")
                                .filters(f->
                                        f.rewritePath("/ccr/(?<segment>.*)",
                                                "/currency-conversion/${segment}")).
                                        uri("lb://currency-conversion"))
                .build();
    }
}
