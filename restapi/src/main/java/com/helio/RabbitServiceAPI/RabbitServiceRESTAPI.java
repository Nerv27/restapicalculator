package com.helio.RabbitServiceAPI;

import com.helio.AppConfig.RabbitServiceConfigRESTAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RabbitServiceRESTAPI {

    @Autowired
    private AmqpTemplate amqpinstance;

    Logger Calcapilogger = LoggerFactory.getLogger(RabbitServiceRESTAPI.class);
    String requestid;
    private String requestresult = "noresponse";

    @Value( "${serv.rabbitmq.queue}" )
    String queueservnameapi;
    @Value( "${serv.rabbitmq.routingkey}" )
    String routingkeyservnameapi;


    /**
     * sends messages to restapi queue
     * @param-message
     */
    public void sendAPIMessage(HashMap message) {
        amqpinstance.convertAndSend(RabbitServiceConfigRESTAPI.ExchangeNamerestapi, routingkeyservnameapi, message);
    }


    /**
     * receive messages from rabbitMq queue
     * @param-incomingMessage
     */
    @RabbitListener(queues = "${serv.rabbitmq.queue}")
    public void recievedMessageAPI(HashMap incomingMessage) {

        try {
            /**
             * process message from calc queue
             *@param-incomingMessage
             */
            Calcapilogger.info("APIHandler >> Retrieving message from queue: " + queueservnameapi);
            requestid = incomingMessage.get("requestid").toString();
            requestresult =  incomingMessage.get("Result").toString();
            Calcapilogger.info("APIHandler >> Request id: " + requestid + " - received message");
            Calcapilogger.info("APIHandler >> Request id: " + requestid + " - Result= " + requestresult);
        }catch (Exception ex){
            Calcapilogger.error("APIHandler >> Error on received message." ,ex);
        }
    }

    /**
     * get message body that is stored on variable requestresult
     *
     */
    public String getRequestresult() {
        return requestresult;
    }
}
