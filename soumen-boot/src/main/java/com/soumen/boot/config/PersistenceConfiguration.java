package com.soumen.boot.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PersistenceConfiguration{
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	@Primary
	public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }
	
	
	
}
