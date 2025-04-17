package com.canama.studentsystem.datenbank.config;

import com.canama.studentsystemcommon.config.RabbitMQBaseConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RabbitMQBaseConfig.class)
public class StudentRabbitMQConfig {

    // Exchange und Routing Keys für alle Operationen:
    public static final String EXCHANGE = "student.exchange";
    public static final String SAVE_ROUTING_KEY = "student.save";
    public static final String GETALL_ROUTING_KEY = "student.getAll";
    public static final String DELETE_ROUTING_KEY = "student.delete";
    public static final String UPDATE_ROUTING_KEY = "student.update";

    // Separate Queues:
    public static final String SAVE_QUEUE = "student.save.queue";
    public static final String GETALL_QUEUE = "student.getAll.queue";
    public static final String DELETE_QUEUE = "student.delete.queue";
    public static final String UPDATE_QUEUE = "student.update.queue";

    @Qualifier(EXCHANGE)
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Qualifier(SAVE_QUEUE)
    @Bean
    public Queue saveQueue() {
        return QueueBuilder.durable(SAVE_QUEUE).build();
    }

    @Qualifier(GETALL_QUEUE)
    @Bean
    public Queue getAllQueue() {
        return QueueBuilder.durable(GETALL_QUEUE).build();
    }

    @Qualifier(DELETE_QUEUE)
    @Bean
    public Queue deleteQueue() {
        return QueueBuilder.durable(DELETE_QUEUE).build();
    }

    @Qualifier(UPDATE_QUEUE)
    @Bean
    public Queue updateQueue() {
        return QueueBuilder.durable(UPDATE_QUEUE).build();
    }


    @Bean
    public Binding bindingSave(
            @Qualifier(EXCHANGE) DirectExchange exchange,
            @Qualifier(SAVE_QUEUE) Queue saveQueue) {

        return BindingBuilder.bind(saveQueue).to(exchange).with(SAVE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingGetAll(
            @Qualifier(EXCHANGE) DirectExchange exchange,
            @Qualifier(GETALL_QUEUE) Queue getAllQueue) {
        return BindingBuilder.bind(getAllQueue).to(exchange).with(GETALL_ROUTING_KEY);
    }

    @Bean
    public Binding bindingDelete(
            @Qualifier(EXCHANGE) DirectExchange exchange,
            @Qualifier(DELETE_QUEUE) Queue deleteQueue) {
        return BindingBuilder.bind(deleteQueue).to(exchange).with(DELETE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingUpdate(
            @Qualifier(EXCHANGE) DirectExchange exchange,
            @Qualifier(UPDATE_QUEUE) Queue updateQueue) {
        return BindingBuilder.bind(updateQueue).to(exchange).with(UPDATE_ROUTING_KEY);
    }

}
