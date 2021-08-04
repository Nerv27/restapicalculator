package com.helio.ServiceCalc;

import com.helio.RabbitServiceCalc.RabbitServiceCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class CalculatorTask {
    @Autowired
    RabbitServiceCalculator amqpservicecalculator;

    Logger Calclogger = LoggerFactory.getLogger(CalculatorTask.class);

    @Value( "${app.rabbitmq.queue}" )
    String queueappname;

    @Scheduled(fixedRate = 5000, initialDelay = 2000)
    public void scheduleTaskCheckAMQPQueue() {

        try {
            Calclogger.info("Listening to queue " + queueappname + " for messages");
            amqpservicecalculator.receiveAPIMessage(queueappname);
        }catch (Exception ex){
            Calclogger.warn("No messages in queue " + queueappname);
        }
    }
}
