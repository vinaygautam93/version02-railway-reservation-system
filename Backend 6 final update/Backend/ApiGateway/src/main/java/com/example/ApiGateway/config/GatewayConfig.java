//package com.example.ApiGateway.config;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.cors.reactive.CorsUtils;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//
//@Configuration
//public class GatewayConfig {
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("user_service", r -> r.path("/user/**")
//                        .uri("http://localhost:8150"))
//                .build();
//    }
//
//    @Bean
//    public WebFilter corsFilter() {
//        return (ServerWebExchange exchange, WebFilterChain chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            if (CorsUtils.isCorsRequest(request)) {
//                ServerHttpResponse response = exchange.getResponse();
//                HttpHeaders headers = response.getHeaders();
//                headers.add("Access-Control-Allow-Origin", "http://localhost:3000");
//                headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//                headers.add("Access-Control-Allow-Headers", "*");
//                headers.add("Access-Control-Allow-Credentials", "true");
//                if (request.getMethod() == HttpMethod.OPTIONS) {
//                    response.setStatusCode(HttpStatus.OK);
//                    return Mono.empty();
//                }
//            }
//            return chain.filter(exchange);
//        };
//    }
//}
