package com.vendaslanches;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


import com.vendaslanches.config.VendasLanchesConfig;

@SpringBootApplication
@EnableConfigurationProperties(VendasLanchesConfig.class)
public class VendasLanchesApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendasLanchesApplication.class, args);
	}

}
