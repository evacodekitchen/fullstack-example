package com.evacodekitchen.diexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
	
	private EncryptionAlgorithm encryptionAlgorithm;

//	public MessageSender(EncryptionAlgorithm encryptionAlgorithm) {
//		this.encryptionAlgorithm = encryptionAlgorithm;
//	}

	public void send(String message) {
		String encryptedMessage = encryptionAlgorithm.encrypt(message);
		System.out.println("Sending message: " + encryptedMessage);
	}
	
	@Autowired
	public void setEncryptionAlgorithm(EncryptionAlgorithm encryptionAlgorithm) {
		this.encryptionAlgorithm = encryptionAlgorithm;
	}

}
