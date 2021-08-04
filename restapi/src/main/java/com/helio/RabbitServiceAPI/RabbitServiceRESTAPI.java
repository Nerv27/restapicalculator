package com.helio.RabbitServiceAPI;

import com.helio.AppConfig.RabbitServiceConfigRESTAPI;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RabbitServiceRESTAPI {

    @Autowired
    private AmqpTemplate amqpinstance;

    /**
     * sends messages via Direct exchange
     * @param message
     */
    public void sendAPIMessage(HashMap message) {
        amqpinstance.convertAndSend(RabbitServiceConfigRESTAPI.ExchangeNamerestapi, "direct", message);

    }
}
