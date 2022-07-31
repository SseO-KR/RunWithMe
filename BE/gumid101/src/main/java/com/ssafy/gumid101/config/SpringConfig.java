package com.ssafy.gumid101.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@EnableAspectJAutoProxy
@Configuration
@EnableJpaAuditing
public class SpringConfig {

//	//ObjectMapper 는 쓰레드 세이프하다. 하위버전에서 락으로 인해 성능이슈가 있었으나 해결됨
//	@Bean 
//	public ObjectMapper objectMapper() {
//		return new ObjectMapper();
//	}
	
}
