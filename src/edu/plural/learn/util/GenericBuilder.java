package edu.plural.learn.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Generic Builder class for building model objects dynamically using Lambdas.
 * @param <T> Generic Type ensuring cohesion throughout the building process.
 */
public class GenericBuilder<T> {

    private final Supplier<T> constructor;
    private List<Consumer<T>> setterDescriptors;

    /**
     * Private constructor for this builder. New instances of this class should be created by <code>GenericBuilder.of</code>
     * @param constructor The constructor Supplier to be used during the building of the object to be instantiated.
     */
    private GenericBuilder( final Supplier<T> constructor ) {
        this.constructor = constructor;
        this.setterDescriptors = new ArrayList<>();
    }

    /**
     * Defines the constructor Supplier to be used by this builder.
     *
     * @param constructor The constructor Supplier to be used during the building of the object to be instantiated.
     * @param <T> Ensures the constructor is of the same class defined as Generic Type of this constructor.
     * @return This very same object.
     */
    public static <T> GenericBuilder<T> of( final Supplier<T> constructor ) {
        return new GenericBuilder<>(constructor);
    }

    /**
     * Accepts a new setter descriptor together with the appropriate value to be setten.
     *
     * @param classSetter <code>Class::setter</code> is expected here.
     * @param value The value to be set by the setter.
     * @param <U> Generic Type ensuring the value provided matches the type expected as argument by the setter method.
     * @return This very same object.
     */
    public <U> GenericBuilder<T> with( final BiConsumer<T, U> classSetter, final U value ) {

        final Consumer<T> setterDescriptor = instance -> classSetter.accept(instance, value);

        this.setterDescriptors.add(setterDescriptor);

        return this;
    }

    /**
     * Instantiates a new object of the class passed as generic type to this builder.
     *
     * @return An instance of Generic Type ensuring this builder works for a specific Class.
     */
    public T build() {
        final T instance = constructor.get();

        this.setterDescriptors.forEach(setterDescriptor -> setterDescriptor.accept(instance));
        this.setterDescriptors.clear();

        return instance;
    }
}
