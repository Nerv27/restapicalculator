package com.helio.AppConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitServiceConfigRESTAPI {

    public static String queueNamerestapi;
    public static String ExchangeNamerestapi;
    public static  String routingkeynamerestapi;

    /**
     * We load tha values for queue, exchange and routing key
     *
     */

    @Autowired
    public RabbitServiceConfigRESTAPI(@Value( "${app.rabbitmq.queue}" ) String queueappname,
                                      @Value( "${app.rabbitmq.exchange}" ) String exchangeappname,
                                      @Value( "${app.rabbitmq.routingkey}" ) String keyappname) {
        this.queueNamerestapi = queueappname;
        this.ExchangeNamerestapi = exchangeappname;
        this.routingkeynamerestapi = keyappname;
    }

    /**
     * We define our queue info
     *
     * @return Queue object
     */
    @Primary
    @Bean
    Queue queuecalc() {
        return new Queue(queueNamerestapi, true);
    }

    /**
     * Creates a direct Exchange
     *
     */
    @Primary
    @Bean
    DirectExchange Exchangerestapi() {
        return new DirectExchange(ExchangeNamerestapi);
    }

    /**
     * Bind topic direct echange to a queue
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Primary
    @Bean
    Binding ExchangeBindingcalcrestapi(Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(routingkeynamerestapi);
    }



}
