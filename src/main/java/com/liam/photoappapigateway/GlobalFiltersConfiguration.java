package com.liam.photoappapigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;

// Pre-filters & Post-filters in one

@Configuration
public class GlobalFiltersConfiguration {
	
final Logger logger = LoggerFactory.getLogger(GlobalFiltersConfiguration.class);
	
	// exchange - Reads the details of HttpRequest object
	// chain - Gateway filter chain - delegates flow to the next filter & chain - Kind of like a next?

	@Bean
	public GlobalFilter secondPrefilter() {
		
		return (exchange, chain) -> {
			
			logger.info("2nd Global PRE-Filter is executed"); // PRE-Filter - 1ST
			
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				
				logger.info("2nd Global POST-Filter is executed"); // POST-Filter - 4TH
			}));
			
		};
		
	}
	
	
	@Bean
	public GlobalFilter thirdPrefilter() {
		
		return (exchange, chain) -> {
			
			logger.info("3rd Global PRE-Filter is executed"); // PRE-Filter - 2ND
			
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				
				logger.info("3rd Global POST-Filter is executed"); // POST-Filter - 3RD
			}));
			
		};
		
	}
}
