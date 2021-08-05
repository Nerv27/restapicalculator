# Java Challange - REST API for Calculator

This is a REST API application with integration with Spring Boot.

Components:

multi module (restapi, Calculator);

multiple listener and sender for amqp queues (restapi, Calculator);

support for rabbitmq;

support for logback-access via logback-access-spring-boot-starter;


How to use:

Multiplication -
/api/calculator/multiple?a=10&b=7

Division -
/api/calculator/divide?a=10&b=7

Sum -
/api/calculator/sum?a=10&b=7

Subtraction -
/api/calculator/subtract?a=10&b=7

