package com.evacodekitchen.diexample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class AppConfig {

	@Bean
	public EncryptionAlgorithm encryptionAlgorithm() {
		return new EncryptionAlgorithm2();
	}

}
