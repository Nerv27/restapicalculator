package com.helio.RabbitServiceCalc;


import com.helio.ServiceCalc.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class RabbitServiceCalculator {

    @Autowired
    private AmqpTemplate amqpinstancecalculator;

    Logger Calcservicelogger = LoggerFactory.getLogger(RabbitServiceCalculator.class);
    CalculatorService CaServ;
    @Value( "${app.rabbitmq.queue}" )
    String queueappname;

    String requestid;
    BigDecimal requestresult;
    BigDecimal requestvalueA;
    BigDecimal requestvalueB;
    String requestsign;
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
        Calcservicelogger.info("Retrieving message from queue: " + queueappname);
        requestid = incomingMessage.get("requestid").toString();
        requestvalueA = new BigDecimal(incomingMessage.get("valueA").toString());
        requestvalueB = new BigDecimal(incomingMessage.get("valueB").toString());;
        requestsign =  incomingMessage.get("sign").toString();
        CaServ = new CalculatorService(requestvalueA,requestvalueB,requestsign);
        Calcservicelogger.info("Request id: " + requestid + " - received message");
        Calcservicelogger.info("Request id: " + requestid + " - ValueA= " + requestvalueA
        + ", ValueB="+ requestvalueB + ", Sign="+  requestsign);
        requestresult =  CaServ.CalculationResult();
        Calcservicelogger.info("Request id: " + requestid + " - Calculator service processed request");
        Calcservicelogger.info("Request id: " + requestid + " - Result: " +  requestresult);
    }
}
