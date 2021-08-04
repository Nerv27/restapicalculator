package com.helio.RabbitServiceCalc;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RabbitServiceCalculator {

    @Autowired
    private AmqpTemplate amqpinstancecalculator;



    /**
     * sends messages via Direct exchange
     * @param queuenameapi
     */
    public void receiveAPIMessage(String queuenameapi) {
         Message All = amqpinstancecalculator.receive(queuenameapi);
         System.out.println("Recieved Message From RabbitMQ: " + All.toString());

    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    /**
     * recive messages from rabbitMq queue
     * @param incomingMessage
     */
    public void recievedMessage(HashMap incomingMessage) {
        String value = incomingMessage.get("Result").toString();
        System.out.println("Recieved Message From RabbitMQ: " + value);
    }
}
