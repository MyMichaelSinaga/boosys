package com.test;
import javax.activation.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BeanConfig {
	@Bean(name = "dataSource")
	@Primary
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource boosys() {
	    return (DataSource) DataSourceBuilder.create().build();
	}
}
