package com.microservices.gateway.utils;

import com.microservices.gateway.constant.CommonConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {

    public String getCorrelationId(HttpHeaders requestHeaders) {
        if (requestHeaders.get(CommonConstants.CORRELATION_ID) == null) {
            return null;
        }
        List<String> requestHeaderList = requestHeaders.get(CommonConstants.CORRELATION_ID);
        return requestHeaderList.stream().findFirst().get();
    }

    public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        return this.setRequestHeader(exchange, CommonConstants.CORRELATION_ID, correlationId);
    }
}
