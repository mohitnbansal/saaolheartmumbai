package com.saaolheart.mumbai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages= {"com.saaolheart.mumbai"})
@EnableJpaRepositories
@EnableAutoConfiguration
public class SaaolHeartMumbaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaaolHeartMumbaiApplication.class, args);
	}		

}

