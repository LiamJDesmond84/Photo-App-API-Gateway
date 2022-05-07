package com.liam.photoappapigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

// Pre-filters are triggered AFTER request are routed through API-Gateway

@Component
public class GlobalPostFilter implements GlobalFilter, Ordered {
	
	final Logger logger = LoggerFactory.getLogger(GlobalPostFilter.class);
	
	// exchange - Reads the details of HttpRequest object
	// chain - Gateway filter chain - delegates flow to the next filter & chain - Kind of like a next?

	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		

		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			
			logger.info("First POST-Filter executed...");
			
		}));
	}

	@Override
	public int getOrder() {
		
		// The lower the # in POST-Filters the lower the priority
		
		return 0;
	}

}
