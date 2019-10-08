package com.evacodekitchen.diexample;

import org.springframework.stereotype.Component;

@Component
public class EncryptionAlgorithm2 implements EncryptionAlgorithm {

	public String encrypt(String message) {
		return message.replace('a', 'c');
	}

}
