package com.helio.ServiceConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitServiceConfigCALC {

    public static String queueNamecalc;
    public static String ExchangeNamecalc;
    public static  String routingkeynamecalc;

    /**
     * We load tha values for queue, exchange and routing key for Calc
     *
     */

    @Autowired
    public RabbitServiceConfigCALC(@Value( "${serv.rabbitmq.queue}" ) String queueappname,
                                   @Value( "${serv.rabbitmq.exchange}" ) String exchangeappname,
                                   @Value( "${serv.rabbitmq.routingkey}" ) String keyappname) {
        this.queueNamecalc = queueappname;
        this.ExchangeNamecalc = exchangeappname;
        this.routingkeynamecalc = keyappname;
    }

    /**
     * Define Calc Queue
     *
     * @return Queue object
     */
    @Bean
    Queue calcqueue() {
        return new Queue(queueNamecalc, true);
    }

    /**
     * Creates a Calc Exchange
     *
     * @return Exchange object
     */
    @Bean
    DirectExchange calcExchangecalc() {
        return new DirectExchange(ExchangeNamecalc);
    }

    /**
     * Bind Calc exchange to a queue
     *
     * @return
     */
    @Bean
    Binding calcExchangeBinding() {
        return BindingBuilder.bind(calcqueue()).to(calcExchangecalc()).with(routingkeynamecalc);
    }
}
