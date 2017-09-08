package com.ian.controller.com.ian.controller;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.ian.beans.Email;

@Component
public class Receiver {

	@JmsListener(destination = "mailbox", containerFactory = "myFactory")
	public void receiveMessage(Email email) {
		System.out.println("Received <" + email + ">");
	}

}