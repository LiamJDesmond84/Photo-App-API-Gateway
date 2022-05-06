package com.liam.photoappapigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalPreFilter implements GlobalFilter {
	
	final Logger logger = LoggerFactory.getLogger(GlobalPreFilter.class);

	// exchange - Reads the details of HttpRequest object
	// chain - Gateway filter chain - delegates flow to the next filter & chain - Kind of like a next?
	
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		logger.info("First pre-filter executed...");
		
		return chain.filter(exchange);
	}

}
