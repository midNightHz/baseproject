package cn.zb.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

	public static void main(String[] args) throws JMSException, InterruptedException {
		ConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin11223344",
				"tcp://106.12.112.114:61616");

		Connection conn = factory.createConnection();

		conn.start();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destonation = session.createQueue("ooxxooxx");

		MessageProducer producer = session.createProducer(destonation);

		producer.setDeliveryMode(DeliveryMode.PERSISTENT);

		producer.setTimeToLive(1000 * 1000 * 1000);
		for (int i = 0; i < 1000; i++) {
			Message message = session.createTextMessage("oooosfasdfasgdhfasa");
			producer.send(message);
			Thread.sleep(1000);
		}
	}

}
