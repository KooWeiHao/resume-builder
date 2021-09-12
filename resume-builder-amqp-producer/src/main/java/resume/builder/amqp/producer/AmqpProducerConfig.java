package resume.builder.amqp.producer;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import resume.builder.amqp.api.service.AmqpProducerService;
import resume.builder.amqp.api.AmqpQueueSuffix;

@Configuration
class AmqpProducerConfig {
    @Value("${amqp.system.code}")
    private String resumeBuilderSystemCode;

    @Value("${amqp.simple.message.listener.container.concurrent.consumers.transaction:1}")
    private int concurrentConsumersForTransaction;

    @Value("${amqp.simple.message.listener.container.concurrent.consumers.information:1}")
    private int concurrentConsumersForInformation;

    @Bean(value = "direct.exchange")
    DirectExchange directExchange() {
        return new DirectExchange(String.format("direct.exchange.%s", resumeBuilderSystemCode));
    }

    @Bean(value = "amqp.invoker.service.exporter")
    AmqpInvokerServiceExporter amqpInvokerServiceExporter(AmqpTemplate amqpTemplate, AmqpProducerService amqpProducerService) {
        AmqpInvokerServiceExporter amqpInvokerServiceExporter = new AmqpInvokerServiceExporter();
        amqpInvokerServiceExporter.setServiceInterface(AmqpProducerService.class);
        amqpInvokerServiceExporter.setService(amqpProducerService);
        amqpInvokerServiceExporter.setAmqpTemplate(amqpTemplate);

        return  amqpInvokerServiceExporter;
    }

    @Bean
    SimpleMessageListenerContainer simpleMessageListenerContainerForTransaction(
            ConnectionFactory connectionFactory, AmqpAdmin amqpAdmin,
            @Qualifier(value = "amqp.invoker.service.exporter") AmqpInvokerServiceExporter amqpInvokerServiceExporter,
            @Qualifier(value = "direct.exchange") DirectExchange directExchange
    ){
        return createSimpleMessageListenerContainer(resumeBuilderSystemCode, connectionFactory, amqpInvokerServiceExporter, amqpAdmin, directExchange, concurrentConsumersForTransaction, AmqpQueueSuffix.TRANSACTION);
    }

    @Bean
    SimpleMessageListenerContainer simpleMessageListenerContainerForInformation(
            ConnectionFactory connectionFactory, AmqpAdmin amqpAdmin,
            @Qualifier(value = "amqp.invoker.service.exporter") AmqpInvokerServiceExporter amqpInvokerServiceExporter,
            @Qualifier(value = "direct.exchange") DirectExchange directExchange
    ){
        return createSimpleMessageListenerContainer(resumeBuilderSystemCode, connectionFactory, amqpInvokerServiceExporter, amqpAdmin, directExchange, concurrentConsumersForInformation, AmqpQueueSuffix.INFORMATION);
    }

    private SimpleMessageListenerContainer createSimpleMessageListenerContainer(
            String systemCode, ConnectionFactory connectionFactory, AmqpInvokerServiceExporter amqpInvokerServiceExporter,
            AmqpAdmin amqpAdmin, DirectExchange directExchange, int concurrentConsumers, String queueSuffix
    ){
        final Queue queue = new Queue(String.format("%s.%s", systemCode, queueSuffix));
        final Binding binding = BindingBuilder.bind(queue).to(directExchange).with(String.format("binding.%s.%s", resumeBuilderSystemCode, queueSuffix));
        amqpAdmin.declareQueue(queue);
        amqpAdmin.declareBinding(binding);

        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setMessageListener(amqpInvokerServiceExporter);
        simpleMessageListenerContainer.setConcurrentConsumers(concurrentConsumers);
        simpleMessageListenerContainer.setQueues(queue);

        return simpleMessageListenerContainer;
    }
}
