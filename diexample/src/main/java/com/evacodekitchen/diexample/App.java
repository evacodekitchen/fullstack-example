package com.evacodekitchen.diexample;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		MessageSender messageSender = applicationContext.getBean(MessageSender.class);
		messageSender.send("some message");
	}
}
