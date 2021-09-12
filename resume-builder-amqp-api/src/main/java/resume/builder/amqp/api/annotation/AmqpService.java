package resume.builder.amqp.api.annotation;

import resume.builder.amqp.api.AmqpQueueSuffix;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Retention(value= RetentionPolicy.RUNTIME)
@Target({TYPE})
public @interface AmqpService {
    String queue() default AmqpQueueSuffix.TRANSACTION;
}
