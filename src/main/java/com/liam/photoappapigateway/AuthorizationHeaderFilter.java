package com.liam.photoappapigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;

import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
	
	
	// We added this
	public static class Config {
		// Put configuration properties here if you want
	}

	// Main business logic for our filter to work
	// Checks to see if request contains an Authorization header then extracts the JWT Token if it does
	@Override
	public GatewayFilter apply(Config config) {
		
		// exchange - Reads HttpRequest object
		// chain - Gateway filter chain - delegates flow to the next filter & chain - Kind of like a next?
		
		return (exchange, chain) -> {
			
			ServerHttpRequest request = exchange.getRequest();
			
//			if(request.getHeaders().containsKey("Authorization")) {
			if(request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				// ServerWebExchange, String, HttpStatus for onError method below
				return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
			}
			
			// Gets entire Authorization header value that also contains the Bearer prefix & JWT Token
//			String authorizationHeader = request.getHeaders().get("Authorization").get(0);
			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			// Removing Bearer to just get the JWT Token
			String jwt = authorizationHeader.replace("Bearer", "");
			
			return chain.filter(exchange);
		};
	}

	// Error response method for non Authorized requests
	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		
		return response.setComplete();
	}
	
}