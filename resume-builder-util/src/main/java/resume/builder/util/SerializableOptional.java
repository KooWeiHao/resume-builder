package resume.builder.util;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/* Reference - https://github.com/nipafx/demo-serialize-optional/blob/master/src/org/codefx/lab/optional/SerializableOptional.java */
public final class SerializableOptional<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = -652697447004597911L;

    private final Optional<T> optional;

    private SerializableOptional(Optional<T> optional) {
        Objects.requireNonNull(optional, "The argument 'optional' must not be null.");
        this.optional = optional;
    }

    public static <T extends Serializable> SerializableOptional<T> fromOptional(Optional<T> optional) {
        return new SerializableOptional<>(optional);
    }

    public static <T extends Serializable> SerializableOptional<T> empty() {
        return new SerializableOptional<>(Optional.empty());
    }

    public static <T extends Serializable> SerializableOptional<T> of(T value) throws NullPointerException {
        return new SerializableOptional<>(Optional.of(value));
    }

    public static <T extends Serializable> SerializableOptional<T> ofNullable(T value) {
        return new SerializableOptional<>(Optional.ofNullable(value));
    }

    public Optional<T> asOptional() {
        return optional;
    }

    // SERIALIZATION
    private Object writeReplace() {
        return new SerializationProxy<>(this);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new InvalidObjectException("Serialization proxy expected.");
    }

    private static class SerializationProxy<T extends Serializable> implements Serializable {
        private static final long serialVersionUID = -1326520485869949065L;

        private final T value;

        public SerializationProxy(SerializableOptional<T> serializableOptional) {
            value = serializableOptional.asOptional().orElse(null);
        }

        private Object readResolve() {
            return SerializableOptional.ofNullable(value);
        }
    }
}
