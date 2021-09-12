package resume.builder.amqp.consumer;

import javafx.util.Pair;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import resume.builder.amqp.api.annotation.AmqpService;
import resume.builder.amqp.api.service.AmqpProducerService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AmqpConsumerCreator {
    @Value("${amqp.rabbit.template.reply.timeout.default:-1}")
    private Long rabbitTemplateDefaultReplyTimeout;

    private final ConnectionFactory connectionFactory;
    private final Map<Pair<String, Class<?>>, Object> cacheMap = new ConcurrentHashMap<>();

    @Autowired
    public AmqpConsumerCreator(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    private class ProducerInvoker implements InvocationHandler {
        private final String className;
        private final AmqpProducerService amqpProducerService;

        private ProducerInvoker(String className, AmqpProducerService amqpProducerService) {
            this.className = className;
            this.amqpProducerService = amqpProducerService;
        }

        @SneakyThrows
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            return amqpProducerService.invoke(className, method.getName(), method.getParameterTypes(), args);
        }
    }

    @SneakyThrows
    public <T> T create(String systemCode, Class<T> clazz) {
        AmqpService amqpService = clazz.getAnnotation(AmqpService.class);
        if(Optional.ofNullable(amqpService).isPresent()){
            final String suffix = amqpService.queue();
            final Object proxy = cacheMap.computeIfAbsent(new Pair(systemCode, clazz), k -> {
                RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
                rabbitTemplate.setExchange(String.format("direct.exchange.%s", systemCode));
                rabbitTemplate.setRoutingKey(String.format("binding.%s.%s", systemCode, suffix));
                rabbitTemplate.setReplyTimeout(rabbitTemplateDefaultReplyTimeout);

                AmqpProxyFactoryBean amqpProxyFactoryBean = new AmqpProxyFactoryBean();
                amqpProxyFactoryBean.setAmqpTemplate(rabbitTemplate);
                amqpProxyFactoryBean.setServiceInterface(AmqpProducerService.class);
                amqpProxyFactoryBean.afterPropertiesSet();

                final ProducerInvoker producerInvoker = new ProducerInvoker(clazz.getName(), (AmqpProducerService) amqpProxyFactoryBean.getObject());
                return Proxy.newProxyInstance(AmqpConsumerCreator.class.getClassLoader(), new Class<?>[]{clazz}, producerInvoker);
            });

            return (T) proxy;
        }
        else{
            throw new Exception(String.format("@AmqpService is not available for '%s'", clazz));
        }
    }
}
