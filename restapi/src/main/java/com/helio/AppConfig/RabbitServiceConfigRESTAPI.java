package com.helio.AppConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitServiceConfigRESTAPI {

    public static String queueNamerestapi;
    public static String ExchangeNamerestapi;
    public static  String routingkeynamerestapi;

    /**
     * We load tha values for queue, exchange and routing key for restapi
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
     * Define restapi queue
     *
     * @return Queue object
     */
    @Bean
    Queue queuecalc() {
        return new Queue(queueNamerestapi, true);
    }

    /**
     * Creates a restapi Exchange
     *
     *  @return Exchange object
     */

    @Bean
    DirectExchange Exchangerestapi() {
        return new DirectExchange(ExchangeNamerestapi);
    }

    /**
     * Bind topic restapi exchange to a queue
     *
     * @return
     */

    @Bean
    Binding ExchangeBindingcalcrestapi() {
        return BindingBuilder.bind(queuecalc()).to(Exchangerestapi()).with(routingkeynamerestapi);
    }
}
