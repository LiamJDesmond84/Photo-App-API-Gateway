package com.liam.photoappapigateway;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

// Pre-filters are triggered before ANY route in this service

@Component
public class GlobalPreFilter implements GlobalFilter {
	
	final Logger logger = LoggerFactory.getLogger(GlobalPreFilter.class);

	// exchange - Reads the details of HttpRequest object
	// chain - Gateway filter chain - delegates flow to the next filter & chain - Kind of like a next?
	
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		logger.info("First PRE-filter executed...");
		
		String requestPath = exchange.getRequest().getPath().toString();
		
		logger.info("Request Path: " + requestPath);
		
		
		HttpHeaders exchangeHeaders = exchange.getRequest().getHeaders();
		
		// To read the key:value of HttpHeaders we must first get a set with header keys from it
		
		Set<String> headerNames = exchangeHeaders.keySet();
		
		logger.info("Http HEADER: " + exchangeHeaders);
		
		// Now iterate over the header keys & get the value of each header
		
		headerNames.forEach((x) -> {
			
			String headerValue = exchangeHeaders.getFirst(x);
			
			logger.info("Header Value: " + headerValue);
			
		});
		

		
		return chain.filter(exchange);
	}

}
