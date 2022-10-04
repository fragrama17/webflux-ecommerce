package com.reactor.tsunami.security;

import com.reactor.tsunami.exception.ForbiddenException;
import com.reactor.tsunami.exception.UnauthorizedException;
import com.reactor.tsunami.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthFilter implements WebFilter {

    @Value("${gateway-ip}")
    private String gatewayAddress;

    private final TokenStore tokenStore;

    private final AdminService adminService;

    public AuthFilter(TokenStore tokenStore, AdminService adminService) {
        this.tokenStore = tokenStore;
        this.adminService = adminService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var request = exchange.getRequest();
        var path = request.getPath().value();

        if (!request.getRemoteAddress().getHostName().equals(gatewayAddress)) {
            log.warn("trying to enter from bad address {}", request.getRemoteAddress().getHostName());
            throw new ForbiddenException("request from not allowed origin");
        }

        var isProductsPath = path.contains("/products");
        var isUsersPath = path.contains("/users");

        var isPost =  Objects.equals(request.getMethod(), HttpMethod.POST);

        var isGetProducts = Objects.equals(request.getMethod(), HttpMethod.GET) && isProductsPath;
        var isLogin = path.contains("/login");
        var isCreateUser = isPost && isUsersPath;
        var isResetPassword = isPost && path.contains("/reset-password");

        if (isGetProducts || isLogin || isCreateUser || isResetPassword) {
            log.info("no need to login");
            return chain.filter(exchange);
        }

        var headers = request.getHeaders();
        var userHeader = headers.get("User");
        var tokenList = headers.get("Authorization");

        if (CollectionUtils.isEmpty(userHeader) || CollectionUtils.isEmpty(tokenList) ||
                !tokenList.get(0).equals(tokenStore.getTokenByUserId(userHeader.get(0))))
            throw new UnauthorizedException("user not authenticated !");

        log.info("user logged in");

        var isModifyUser = Objects.equals(request.getMethod(), HttpMethod.PUT) && isUsersPath;
        var isOrdersPath = path.contains("/orders");
        if (!isModifyUser && !isOrdersPath) {
            log.info("checking if administrator for add, modify and delete a product");
            return adminService.checkIfAdmin(userHeader.get(0))
                    .then(chain.filter(exchange));
        }

        return chain.filter(exchange);
    }
}
