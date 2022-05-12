package com.liam.photoappapigateway;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpTrace {
	
	@Bean
	public HttpTraceRepository htttpTraceRepository()
    {
      return new InMemoryHttpTraceRepository();
    }

}
