package com.canama.studentsystem.datenbank.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CourseRabbitMQConfig {

    // Exchange und Routing Keys für Courses:
    public static final String EXCHANGE = "course.exchange";
    public static final String SAVE_ROUTING_KEY = "course.save";
    public static final String GETALL_ROUTING_KEY = "course.getAll";
    public static final String DELETE_ROUTING_KEY = "course.delete";
    public static final String ADD_STUDENT_ROUTING_KEY = "course.addStudent";

    // Queues für Courses:
    public static final String SAVE_QUEUE = "course.save.queue";
    public static final String GETALL_QUEUE = "course.getAll.queue";
    public static final String DELETE_QUEUE = "course.delete.queue";
    public static final String ADD_STUDENT_QUEUE = "course.addStudent.queue";

    @Bean
    public DirectExchange courseExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue courseSaveQueue() {
        return QueueBuilder.durable(SAVE_QUEUE).build();
    }

    @Bean
    public Queue courseGetAllQueue() {
        return QueueBuilder.durable(GETALL_QUEUE).build();
    }

    @Bean
    public Queue courseDeleteQueue() {
        return QueueBuilder.durable(DELETE_QUEUE).build();
    }

    @Bean
    public Queue courseAddStudentQueue() {
        return QueueBuilder.durable(ADD_STUDENT_QUEUE).build();
    }

    @Bean
    public Binding bindingCourseSave(DirectExchange courseExchange, Queue courseSaveQueue) {
        return BindingBuilder.bind(courseSaveQueue).to(courseExchange).with(SAVE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingCourseGetAll(DirectExchange courseExchange, Queue courseGetAllQueue) {
        return BindingBuilder.bind(courseGetAllQueue).to(courseExchange).with(GETALL_ROUTING_KEY);
    }

    @Bean
    public Binding bindingCourseDelete(DirectExchange courseExchange, Queue courseDeleteQueue) {
        return BindingBuilder.bind(courseDeleteQueue).to(courseExchange).with(DELETE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingCourseAddStudent(DirectExchange courseExchange, Queue courseAddStudentQueue) {
        return BindingBuilder.bind(courseAddStudentQueue).to(courseExchange).with(ADD_STUDENT_ROUTING_KEY);
    }

    // JSON Converter für die automatische Umwandlung von Nachrichten in DTOs:
    @Bean
    public Jackson2JsonMessageConverter courseJsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
