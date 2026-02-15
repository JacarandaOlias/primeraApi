package com.jacaranda.primeraPrueba.exception;

import java.util.List;

public class BadRequestElementException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private List<String> messages;

	public BadRequestElementException(List<String> messages) {
		super();
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	


}
