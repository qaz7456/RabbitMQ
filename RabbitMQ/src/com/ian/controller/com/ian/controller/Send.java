package com.ian.controller.com.ian.controller;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;

public class Send {
	private final static String QUEUE_NAME = "Kevin";
	private final static String EXCHANGENAME = "datas";
	private final static String ROUTINGKEY = "Kevin";

	public static void main(String[] args) throws IOException, TimeoutException {
		// TODO Auto-generated method stub

		String userName = "admin";
		String password = "password";
		String virtualHost = "";
		String hostName = "192.168.112.199";
		int portNumber = 5672;

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(hostName);
		factory.setPort(portNumber);
		factory.setUsername(userName);
		factory.setPassword(password);

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, true, false, false, null);

		byte[] messageBodyBytes = "能看到嗎?2".getBytes();
		
		channel.basicPublish(EXCHANGENAME, ROUTINGKEY, MessageProperties.PERSISTENT_TEXT_PLAIN, messageBodyBytes);

		 channel.close();
		 connection.close();

	}

}
