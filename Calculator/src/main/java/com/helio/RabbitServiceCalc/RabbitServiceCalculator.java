package com.helio.RabbitServiceCalc;


import com.helio.ServiceCalc.CalculatorService;
import com.helio.ServiceConfig.RabbitServiceConfigCALC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
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
    @Value( "${serv.rabbitmq.queue}" )
    String queueservname;

    String requestid;
    BigDecimal requestresult;
    BigDecimal requestvalueA;
    BigDecimal requestvalueB;
    String requestsign;
    HashMap mapservresponse = new HashMap<>();

    /**
     * sends messages via Direct exchange
     * @param-message
     */
    public void sendMessageCalc(HashMap message) {
        amqpinstancecalculator.convertAndSend(RabbitServiceConfigCALC.ExchangeNamecalc,"direct", message);

    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    /**
     * receive messages from rabbitMq queue
     * @param incomingMessage
     */
    public void recievedMessageCalc(HashMap incomingMessage) {

        try {
            /**
             * process request
             *
             */
            Calcservicelogger.info("ServiceHandler >> Retrieving message from queue: " + queueappname);
            requestid = incomingMessage.get("requestid").toString();
            requestvalueA = new BigDecimal(incomingMessage.get("valueA").toString());
            requestvalueB = new BigDecimal(incomingMessage.get("valueB").toString());;
            requestsign =  incomingMessage.get("sign").toString();
            CaServ = new CalculatorService(requestvalueA,requestvalueB,requestsign);
            Calcservicelogger.info("ServiceHandler >> Request id: " + requestid + " - received message");
            Calcservicelogger.info("ServiceHandler >> Request id: " + requestid + " - ValueA= " + requestvalueA
                    + ", ValueB="+ requestvalueB + ", Sign="+  requestsign);
            requestresult =  CaServ.CalculationResult();
            Calcservicelogger.info("ServiceHandler >> Request id: " + requestid + " - Calculator service processed request");
            Calcservicelogger.info("ServiceHandler >> Request id: " + requestid + " - Result: " +  requestresult);

            /**
             * send result to rabbitMq queue
             *
             */
            Calcservicelogger.info("ServiceHandler >> Request id: " + requestid + " - Calculator service sending response to queue "
            + queueservname);
            mapservresponse.put("requestid",requestid);
            mapservresponse.put("Result",requestresult);
            sendMessageCalc(mapservresponse);
        }catch (Exception ex){
            Calcservicelogger.error("ServiceHandler >> Error on received message." ,ex);
        }
    }
}
