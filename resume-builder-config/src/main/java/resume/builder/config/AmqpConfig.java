package resume.builder.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AmqpConfig {
    private static final Logger logger = LoggerFactory.getLogger(AmqpConfig.class);

    @Value("${amqp.connection.factory.addresses:localhost:5672}")
    private String connectionFactoryAddresses;

    @Value("${amqp.connection.factory.username:resume-builder}")
    private String connectionFactoryUsername;

    @Value("${amqp.connection.factory.password:resume-builder}")
    private String connectionFactoryPassword;

    @Bean
    ConnectionFactory connectionFactory() {
        if(StringUtils.isNotBlank(connectionFactoryAddresses) && StringUtils.isNotBlank(connectionFactoryUsername) && StringUtils.isNotBlank(connectionFactoryPassword))
        {
            CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
            connectionFactory.setAddresses(connectionFactoryAddresses);
            connectionFactory.setUsername(connectionFactoryUsername);
            connectionFactory.setPassword(connectionFactoryPassword);
            return connectionFactory;
        }
        else
        {
            logger.info("Missing AMQP connection properties");
            return new CachingConnectionFactory();
        }
    }

    @Bean
    AmqpAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
