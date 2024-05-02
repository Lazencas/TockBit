package org.example.filter;

import org.example.jwtutil.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalAuthFilter extends AbstractGatewayFilterFactory<GlobalAuthFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public GlobalAuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();//요청 객체를 가져옴
            String token = request.getHeaders().getFirst(JwtUtil.AUTHORIZATION_HEADER);//요청 헤더에서 토큰 가져옴
            System.out.println("확인"+token);
            if (token != null && jwtUtil.validateToken(jwtUtil.substringToken(token))) {
                //토큰이 null이 아니고, 유효한 경우 요청을 계속 진행
                System.out.println("유효한거 확인");
                return chain.filter(exchange);
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
            }
        };
    }

    public static class Config {
        // Put the configuration properties
    }


}
