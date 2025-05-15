package com.cloud.gateway.filter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.Claims;
import com.cloud.gateway.common.StringUtils;
import com.cloud.gateway.config.properties.IgnoreWhiteProperties;
import com.cloud.gateway.common.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 网关鉴权
 * 
 * @author cloud
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered
{
    // 排除过滤的 uri 地址，nacos自行添加
    @Autowired
    private IgnoreWhiteProperties ignoreWhite;

    @Autowired
    private RedisService redisService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();

        String url = request.getURI().getPath();
        // 跳过不需要验证的路径
        if (StringUtils.matches(url, ignoreWhite.getWhites())) {
            return chain.filter(exchange);
        }
        //前台
        String token = request.getHeaders().getFirst("frontAuth");
        String umsUserDataJson = redisService.get(token);
        if (StringUtils.isNotBlank(umsUserDataJson)) {
            JSONObject jsonObject = JSONUtil.parseObj(umsUserDataJson);
            Object userId = jsonObject.get("userId");
            Object username = jsonObject.get("userName");
            if (userId != null && username != null) {
                // 设置用户信息到请求
                addHeader(mutate, "userId", userId.toString());
                addHeader(mutate, "username", username.toString());
                addHeader(mutate, "frontAuth", "1");
            }
        }
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value)
    {
        if (value == null)
        {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = urlEncode(valueStr);
        mutate.header(name, valueEncode);
    }

    public static String urlEncode(String str)
    {
        try
        {
            return URLEncoder.encode(str, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            return StringUtils.EMPTY;
        }
    }

    @Override
    public int getOrder()
    {
        return -200;
    }
}