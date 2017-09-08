import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeoutException;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

public class coedeMain {

	private static String QUEUE_NAME = "RobinQueue";

	private static String USERNAME = "admin";
	private static String PASSWORD = "password";
	private static String HOST = "192.168.112.199";
	private static Integer PORTNUMBER = 5672;
	private static String ROUTING_KEY = "RobinQueue";
	private static String EXCHANGE_NAME = "datas";
	private static boolean NOACK = false;
	private static boolean ACK = true;
	private static boolean DURABLE = true;

	private static final Logger logger = LogManager.getLogger(coedeMain.class);

	private static java.util.Queue<String> robinQueue = new LinkedList<String>();
	private static java.util.Queue<String> robinQueue1 = new LinkedList<String>();
	private static java.util.Queue<String> ianQueue = new LinkedList<String>();

	public static void main(String[] args) throws IOException, TimeoutException, ShutdownSignalException,
			ConsumerCancelledException, InterruptedException, JMSException {

		 try {
		 /*MQ Configuration*/
		 ApplicationContext context = new
		 ClassPathXmlApplicationContext("beans-config.xml");
		 RMQConnectionFactory rMQConnectionFactory = (RMQConnectionFactory)
		 context.getBean("jmsConnectionFactory");
		 // RMQConnectionFactory mqQueueConnectionFactory = new
		 // RMQConnectionFactory();
		 // mqQueueConnectionFactory.setHost(HOST);
		 // mqQueueConnectionFactory.setPort(PORTNUMBER);
		 // mqQueueConnectionFactory.setUsername(USERNAME);
		 // mqQueueConnectionFactory.setPassword(PASSWORD);
		
		 /* Create Connection */
		 QueueConnection queueConnection =
		 rMQConnectionFactory.createQueueConnection();
		 queueConnection.start();
		
		 // /*Create session */
		 QueueSession queueSession = queueConnection.createQueueSession(false,
		 Session.AUTO_ACKNOWLEDGE);
		
		 // /*Create response queue */
//		 Queue queue = queueSession.createQueue("KevinReceive");
		 /* Create text message */
		 TextMessage textMessage = queueSession.createTextMessage("put some message here");
//		 textMessage.setJMSReplyTo(queue);
//		 textMessage.setJMSType("mcd://xmlns");// message type
//		 textMessage.setJMSExpiration(2 * 1000);// message expiration
		 
		 RMQDestination rmqDestination = new RMQDestination();
		 rmqDestination.setDestinationName("ian");
		 rmqDestination.setAmqpExchangeName("datas");
		 rmqDestination.setAmqpRoutingKey("ian");
		 rmqDestination.setAmqpQueueName("ian");
		 
		 
//		 destinationName - 隊列目標的名稱
//		 amqpExchangeName - 映射資源的交換名稱
//		 amqpRoutingKey - 映射資源的路由密鑰
//		 amqpQueueName - 映射資源的隊列名稱（用於監聽消息）
		 
		 
		 
//		 public  RMQDestination (String destinationName,String amqpExchangeName,String amqpRoutingKey,String amqpQueueNam);
		 // message delivery mode either persistent or non-persistemnt
		 textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
		
		 /* Create sender queue */
		 QueueSender queueSender =
		 queueSession.createSender(queueSession.createQueue("KevinReceive"));
		 queueSender.setTimeToLive(2 * 1000);
		 queueSender.send(textMessage);
		 
		 MessageProducer producer = queueSession.createProducer(rmqDestination);
			producer.send(textMessage);
			
		 /* After sending a message we get message id */
		 System.out.println("after sending a message we get message id " + textMessage.getJMSMessageID());
//		 String jmsCorrelationID = " JMSCorrelationID = '" + textMessage.getJMSMessageID() + "'";
		
		 // /* Within the session we have to create queue reciver */
		// QueueReceiver queueReceiver = queueSession.createReceiver(queue, jmsCorrelationID);
		//
		// /* Receive the message from */
		// Message message = queueReceiver.receive(60 * 1000);
		// String responseMsg = ((TextMessage) message).getText();
		// logger.debug("responseMsg: " + responseMsg);
		 queueSender.close();
		// queueReceiver.close();
		 queueSession.close();
		 // queueConnection.close();
		
		 } catch (JMSException e) {
		 e.printStackTrace();
		 } catch (Exception e) {
		 e.printStackTrace();
		 }

		// =================================================================================
//		ConnectionFactory factory = new ConnectionFactory();
//		factory.setUsername(USERNAME);
//		factory.setPassword(PASSWORD);
//		factory.setHost(HOST);
//		factory.setPort(PORTNUMBER);
//
//		Connection conn = factory.newConnection();
//
//		Channel channel = conn.createChannel();
//
//		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
//		String queueName = channel.queueDeclare().getQueue();
//
//		String[] keys = new String[] { "RobinQueue", "RobinQueue1", "IanQueue" };
//		for (String key : keys) {
//			channel.queueBind(queueName, EXCHANGE_NAME, key);
//		}
//
//		// channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//		//
//		// String queue_name = channel.queueDeclare().getQueue();
//
//		// 創建交換
//		// channel.exchangeDeclare("logs", "fanout", true);
//
//		// 臨時隊列，並讓服務端給我們選擇一個隨機的隊列名字
//		// String queueName = channel.queueDeclare().getQueue();
//		// channel.queueBind(queueName, "logs", "RobinQueue");
//		//
//		// logger.debug("queue_name: " + queue_name);
//
//		System.out.println();
//		Consumer consumer = new DefaultConsumer(channel) {
//			@Override
//			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
//					byte[] body) throws IOException {
//				String message = new String(body, "UTF-8");
//				String routingKey = envelope.getRoutingKey();
//				long deliveryTag = envelope.getDeliveryTag();
//
//				logger.debug("routingKey: {} \\ Received: {} \\ deliveryTag: {} \\  consumerTag: {}", routingKey,
//						message, deliveryTag, consumerTag);
//
//				if ("RobinQueue".equals(routingKey)) {
//					robinQueue.add(message);
//					logger.debug("robinQueue size: " + robinQueue.size());
//					System.out.println();
//				}
//				if ("RobinQueue1".equals(routingKey)) {
//					robinQueue1.add(message);
//					logger.debug("robinQueue1 size: " + robinQueue1.size());
//					System.out.println();
//				}
//				if ("IanQueue".equals(routingKey)) {
//					ianQueue.add(message);
//					logger.debug("ianQueue size: " + ianQueue.size());
//					System.out.println();
//				}
//				 channel.basicAck(deliveryTag, false);
//
//			}
//		};
//		channel.basicConsume(queueName, ACK, consumer);

		// =================================================================================

//		 channel.basicConsume(queueName, NOACK, new DefaultConsumer(channel)
//		 {
//		
//				@Override
//				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
//						byte[] body) throws IOException {
//					String message = new String(body, "UTF-8");
//					String routingKey = envelope.getRoutingKey();
//					long deliveryTag = envelope.getDeliveryTag();
//
//					logger.debug("routingKey: {} \\ Received: {} \\ deliveryTag: {} \\  consumerTag: {}", routingKey,
//							message, deliveryTag, consumerTag);
//
//					if ("RobinQueue".equals(routingKey)) {
//						robinQueue.add(message);
//						logger.debug("robinQueue size: " + robinQueue.size());
//						System.out.println();
//					}
//					if ("RobinQueue1".equals(routingKey)) {
//						robinQueue1.add(message);
//						logger.debug("robinQueue1 size: " + robinQueue1.size());
//						System.out.println();
//					}
//					if ("IanQueue".equals(routingKey)) {
//						ianQueue.add(message);
//						logger.debug("ianQueue size: " + ianQueue.size());
//						System.out.println();
//					}
//					 channel.basicAck(deliveryTag, false);
//
//				}
//		 }); 
		// channel.close();
		// conn.close();

		// while (true) {
		// QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		// // process delivery
		// channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		//
		// String message = new String(delivery.getBody(), "UTF-8");
		// System.out.println("Received: " + message);
		// }

		// channel.basicConsume(queue_name, NOACK, new DefaultConsumer(channel)
		// {
		//
		// @Override
		// public void handleDelivery(String consumerTag, Envelope envelope,
		// AMQP.BasicProperties properties,
		// byte[] body) throws IOException {
		//
		// String message = new String(body, "UTF-8");
		// System.out.println("Received: " + message);
		//
		// channel.basicAck(envelope.getDeliveryTag(), false);
		// }
		// });

		// Consumer consumer = new QueueingConsumer(channel);
		//
		// channel.basicConsume(queue_name, NOACK, consumer);
		// while (true) {
		// QueueingConsumer.Delivery delivery = ((QueueingConsumer)
		// consumer).nextDelivery();
		// // process delivery
		// channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		// System.out.println("Message received" + new
		// String(delivery.getBody()));
		// }

		// Consumer consumer = new DefaultConsumer(channel) {
		// @Override
		// public void handleDelivery(String consumerTag, Envelope envelope,
		// AMQP.BasicProperties properties,
		// byte[] body) throws IOException {
		// String message = new String(body, "UTF-8");
		// System.out.println("Received: " + message);
		//
		// }
		// // channel.close();
		// // conn.close();
		// };

	}
}
