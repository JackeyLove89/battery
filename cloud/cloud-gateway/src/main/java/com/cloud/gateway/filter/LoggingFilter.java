package com.cloud.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求路径
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 打印日志
        logger.info("Request Path: {}", path);

        // 继续执行过滤器链
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // 设置过滤器的执行顺序（值越小优先级越高）
        return -1;
    }
}
