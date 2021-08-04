package com.helio.RabbitServiceAPI;

import com.helio.AppConfig.RabbitServiceConfigRESTAPI;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitServiceRESTAPI {

    @Autowired
    private AmqpTemplate amqpinstance;

    /**
     * sends messages via Direct exchange
     * @param message
     */
    public void sendAPIMessage(String message) {
        amqpinstance.convertAndSend(RabbitServiceConfigRESTAPI.ExchangeNamerestapi, "direct", message);

    }
}
