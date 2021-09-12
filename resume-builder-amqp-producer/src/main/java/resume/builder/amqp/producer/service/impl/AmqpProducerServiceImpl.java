package resume.builder.amqp.producer.service.impl;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import resume.builder.amqp.api.service.AmqpProducerService;
import resume.builder.amqp.api.annotation.AmqpService;

import java.util.Optional;

@Service
public class AmqpProducerServiceImpl implements AmqpProducerService {
    private static final Logger logger = LoggerFactory.getLogger(AmqpProducerServiceImpl.class);

    private final ApplicationContext applicationContext;

    @Autowired
    public AmqpProducerServiceImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @SneakyThrows
    @Override
    public Object invoke(String className, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        final Optional<Class<?>> clazzo= loadClass(className);
        if(clazzo.isPresent()){
            final Class<?> clazz = clazzo.get();

            return clazz.getDeclaredMethod(methodName, parameterTypes)
                    .invoke(applicationContext.getBean(clazz), parameters);
        }

        throw new Exception(String.format("Class '%s' was not found or @AmqpService is not available", className));
    }

    private Optional<Class<?>> loadClass(String className) {
        try{
            Class<?> clazz = Class.forName(className);
            if(clazz.isAnnotationPresent(AmqpService.class)){
                return Optional.of(clazz);
            }
        }
        catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        return Optional.empty();
    }
}
