package com.canama.studentsystemcommon.config;


import jakarta.annotation.PostConstruct;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Slf4j
@Configuration
public class RabbitMQBaseConfig implements RabbitListenerConfigurer {
    @Value("${spring.rabbitmq.host:localhost}")
    private String hostname;

    @Value("${spring.rabbitmq.username:guest}")
    private String username;

    @Value("${spring.rabbitmq.password:guest}")
    private String password;

    @Value("${spring.application.name:spring.application.name.missing}")
    private String appName;

    private final String instanceId = UUID.randomUUID().toString().substring(0, 8);
    private final AtomicInteger connectionNumber = new AtomicInteger(0);
    private static final String CONNECTION_NAME_FORMAT = "%s:%s:%02d";

    public RabbitMQBaseConfig() {
    }

    private String getConnectionName() {
        String connectionName = String.format(CONNECTION_NAME_FORMAT, this.appName, this.instanceId, this.connectionNumber.incrementAndGet());
        log.info("RabbitMQ connection name: {}", connectionName);
        return connectionName;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(this.getHostname());
        connectionFactory.setUsername(this.getUsername());
        connectionFactory.setPassword(this.getPassword());
        connectionFactory.setRequestedHeartBeat(10);
        connectionFactory.setChannelCacheSize(20);
        connectionFactory.setConnectionLimit(100);
        connectionFactory.setConnectionNameStrategy((f) -> this.getConnectionName());
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(this.connectionFactory());
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(this.jsonMessageConverter());
        factory.setConnectionFactory(this.connectionFactory());
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        return factory;
    }

    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(this.myHandlerMethodFactory());
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(this.mappingJackson2MessageConverter());
        return factory;
    }

    @Bean(name = {"provisioningExchange"})
    public DirectExchange provisioningExchange() {
        return new DirectExchange("provisioning");
    }

    @Bean
    public RabbitTemplate rabbitTemplate2() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory());
        rabbitTemplate.setMessageConverter(this.jsonMessageConverter());
        return rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        log.debug("{} started", this.getClass().getSimpleName());
    }

    @Generated
    public String getHostname() {
        return this.hostname;
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public String getAppName() {
        return this.appName;
    }

    @Generated
    public String getInstanceId() {
        return this.instanceId;
    }

    @Generated
    public AtomicInteger getConnectionNumber() {
        return this.connectionNumber;
    }
}