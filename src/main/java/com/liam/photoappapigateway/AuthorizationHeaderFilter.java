package com.liam.photoappapigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
	
	
	// We added this
	public static class Config {
		// Put configuration properties here
	}

	// Main business logic for our filter to work
	@Override
	public GatewayFilter apply(Config config) {
		// TODO Auto-generated method stub
		return null;
	}

}
