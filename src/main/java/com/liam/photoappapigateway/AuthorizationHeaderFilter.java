package com.liam.photoappapigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
	
	@Autowired
	Environment env;
	
	
	public AuthorizationHeaderFilter() {
		super(Config.class);
	}
	
	
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
			
			// Just for testing....
//			org.springframework.http.HttpHeaders test = exchange.getRequest().getHeaders();
//			System.out.println(test);
//			org.springframework.http.HttpHeaders test2 = request.getHeaders();
			
			
//			if(request.getHeaders().containsKey("Authorization")) {
			if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				// ServerWebExchange, String, HttpStatus for onError method below
				return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
			}
			
			// Gets entire Authorization header value that also contains the Bearer prefix & JWT Token
//			String authorizationHeader = request.getHeaders().get("Authorization").get(0);
			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			
			// Removing Bearer to just get the JWT Token
			String jwt = authorizationHeader.replace("Bearer", "");
			
			// Checking if JWT Token exists with below method
			if(!isJwtValid(jwt)) {
				return onError(exchange, "JWT Token is not valid", HttpStatus.UNAUTHORIZED);
			};
			
			return chain.filter(exchange);
		};
	}

	// Error response method for non Authorized requests
	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		
		return response.setComplete();
	}
	
	
	// Checking to see if JWT Token received from header is valid(exists)
	private boolean isJwtValid(String jwt) {
		
		boolean boolAnswer = true;
		
		// Jwts.parser() from dependencies
		// Making sure secret key in properties matches secret key(signature) from create/register method for users(AuthenticationFilter .signWith)
		String subject = Jwts.parser()
		.setSigningKey(env.getProperty("token.secret"))
		.parseClaimsJws(jwt)
		.getBody()
		.getSubject();
		
		if(subject == null || subject.isEmpty()) {
			boolAnswer = false;
		}
		
		return boolAnswer;
		
	}
	
}