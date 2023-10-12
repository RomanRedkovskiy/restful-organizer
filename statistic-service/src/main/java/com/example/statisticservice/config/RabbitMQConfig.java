package com.example.statisticservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TASK_QUEUE = "taskChangeQueue";
    public static final String COMPILATION_QUEUE = "compilationChangeQueue";
    public static final String TASK_ROUTING_KEY = "taskChangeRoutingKey";
    public static final String COMPILATION_ROUTING_KEY = "compilationChangeRoutingKey";
    public static final String EXCHANGE = "organizerExchange";

    @Bean
    public Queue taskQueue() {
        return new Queue(TASK_QUEUE);
    }

    @Bean
    public Queue compilationQueue() {
        return new Queue(COMPILATION_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding taskBinding(Queue taskQueue, TopicExchange exchange) {
        return BindingBuilder.bind(taskQueue).to(exchange).with(TASK_ROUTING_KEY);
    }

    @Bean
    public Binding compilationBinding(Queue compilationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(compilationQueue).to(exchange).with(COMPILATION_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
