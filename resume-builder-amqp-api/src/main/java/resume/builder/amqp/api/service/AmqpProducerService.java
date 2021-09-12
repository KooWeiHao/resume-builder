package resume.builder.amqp.api.service;

public interface AmqpProducerService {
    Object invoke(final String className, final String methodName, final Class<?>[] parameterTypes, final Object[] parameters);
}
