/*
 * Copyright © 2024 io.jsonwebtoken and Les Hazlewood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package scrapi.util;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * Utility methods for providing argument and state assertions to reduce repeating these patterns and otherwise
 * increasing cyclomatic complexity.
 *
 * @since SCRAP_RELEASE_VERSION
 */
public final class Assert {

    private Assert() {
    } //prevent instantiation

    /**
     * Assert that an object is not <code>null</code> .
     * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
     *
     * @param object  the object to check
     * @param <T>     the type of object
     * @param message the exception message to use if the assertion fails
     * @return the non-null object
     * @throws NullPointerException if the object is <code>null</code>
     */
    public static <T> T notNull(T object, String message) {
        hasText(message, "Exception message argument cannot be null or empty.");
        return java.util.Objects.requireNonNull(object, message);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T> T notNull(Optional<T> op, String msg) {
        notNull(op, "Optional argument cannot be null.");
        return op.orElseThrow(() -> new IllegalArgumentException(msg));
    }

    /**
     * Assert that the given String is not empty; that is,
     * it must not be <code>null</code> and not the empty String.
     * <pre class="code">Assert.hasLength(name, "Name must not be empty");</pre>
     *
     * @param text    the String to check
     * @param message the exception message to use if the assertion fails
     * @see Strings#hasLength
     */
    public static void hasLength(String text, String message) {
        hasText(message, "Exception message argument cannot be null or empty.");
        if (!Strings.hasLength(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that the given String has valid text content; that is, it must not
     * be <code>null</code> and must contain at least one non-whitespace character.
     * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
     *
     * @param <T>     the type of CharSequence
     * @param text    the CharSequence to check
     * @param message the exception message to use if the assertion fails
     * @return the CharSequence if it has text
     * @see Strings#hasText
     */
    public static <T extends CharSequence> T hasText(T text, String message) {
        if (!Strings.hasText(message)) {
            throw new IllegalArgumentException("Exception message argument cannot be null or empty.");
        }
        if (!Strings.hasText(text)) {
            throw new IllegalArgumentException(message);
        }
        return text;
    }

    /**
     * Assert that an array is not {@code null} nor zero length.
     *
     * @param array the array to check
     * @param msg   the exception message to use if the assertion fails
     * @return the non-empty array for immediate use
     * @throws NullPointerException     if the {@code array} is {@code null}.
     * @throws IllegalArgumentException if the {@code array} is empty.
     */
    public static <T> T[] notEmpty(T[] array, String msg) {
        hasText(msg, "Exception message cannot be null or empty.");
        notNull(array, msg);
        if (array.length == 0) throw new IllegalArgumentException(msg);
        return array;
    }

    /**
     * Assert that a byte array is not {@code null} nor zero length.
     *
     * @param array the byte array to check
     * @param msg   the exception message to use if the assertion fails
     * @return the non-empty array for immediate use
     * @throws NullPointerException     if the {@code array} is {@code null}.
     * @throws IllegalArgumentException if the {@code array} is empty.
     */
    public static byte[] notEmpty(byte[] array, String msg) {
        hasText(msg, "Exception message cannot be null or empty.");
        notNull(array, msg);
        if (array.length == 0) throw new IllegalArgumentException(msg);
        return array;
    }

    public static char[] notEmpty(char[] array, String msg) {
        hasText(msg, "Exception message cannot be null or empty.");
        notNull(array, msg);
        if (array.length == 0) throw new IllegalArgumentException(msg);
        return array;
    }

    /**
     * Assert that a collection has elements; that is, it must not be
     * <code>null</code> and must have at least one element.
     * <pre class="code">Assert.notEmpty(collection, "Collection must have elements");</pre>
     *
     * @param collection the collection to check
     * @param <T>        the type of collection
     * @param message    the exception message to use if the assertion fails
     * @return the non-null, non-empty collection
     * @throws IllegalArgumentException if the collection is <code>null</code> or has no elements
     */
    public static <T extends Collection<?>> T notEmpty(T collection, String message) {
        hasText(message, "Exception message cannot be null or empty.");
        if (Collections.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
        return collection;
    }

    /**
     * Assert that a Map has entries; that is, it must not be <code>null</code>
     * and must have at least one entry.
     * <pre class="code">Assert.notEmpty(map, "Map must have entries");</pre>
     *
     * @param map     the map to check
     * @param <T>     the type of Map to check
     * @param message the exception message to use if the assertion fails
     * @return the non-null, non-empty map
     * @throws IllegalArgumentException if the map is <code>null</code> or has no entries
     */
    public static <T extends Map<?, ?>> T notEmpty(T map, String message) {
        hasText(message, "Exception message cannot be null or empty.");
        if (Collections.isEmpty(map)) {
            throw new IllegalArgumentException(message);
        }
        return map;
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     * <pre class="code">Assert.instanceOf(Foo.class, foo);</pre>
     *
     * @param type    the type to check against
     * @param <T>     the object's expected type
     * @param obj     the object to check
     * @param message a message which will be prepended to the message produced by
     *                the function itself, and which may be used to provide context. It should
     *                normally end in a ": " or ". " so that the function generate message looks
     *                ok when prepended to it.
     * @return the non-null object IFF it is an instance of the specified {@code type}.
     * @throws IllegalArgumentException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static <T> T isInstance(Class<T> type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        hasText(message, "Exception message cannot be null or empty.");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(message +
                    "Object of class " + Objects.nullSafeClassName(obj) + " must be an instance of " + type);
        }
        return type.cast(obj);
    }

    /**
     * Asserts that the provided object is an instance of the provided class, throwing an
     * {@link IllegalStateException} otherwise.
     * <pre class="code">Assert.stateIsInstance(Foo.class, foo);</pre>
     *
     * @param type    the type to check against
     * @param <T>     the object's expected type
     * @param obj     the object to check
     * @param message a message which will be prepended to the message produced by
     *                the function itself, and which may be used to provide context. It should
     *                normally end in a ": " or ". " so that the function generate message looks
     *                ok when prepended to it.
     * @return the non-null object IFF it is an instance of the specified {@code type}.
     * @throws IllegalStateException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static <T> T stateIsInstance(Class<T> type, Object obj, String message) {
        notNull(type, "Type to check cannot be null.");
        if (!type.isInstance(obj)) {
            String msg = message + "Object of class " + Objects.nullSafeClassName(obj) +
                    " must be an instance of " + type;
            throw new IllegalStateException(msg);
        }
        return type.cast(obj);
    }

    /**
     * Asserts that a specified {@code value} is equal to the given {@code requirement}, throwing
     * an {@link IllegalArgumentException} with the given message if not.
     *
     * @param <T>         the type of argument
     * @param value       the value to check
     * @param requirement the requirement that {@code value} must be greater than
     * @param msg         the message to use for the {@code IllegalArgumentException} if thrown.
     * @return {@code value} if greater than the specified {@code requirement}.
     * @since 0.12.0
     */
    public static <T extends Comparable<T>> T eq(T value, T requirement, String msg) {
        if (compareTo(value, requirement) != 0) {
            throw new IllegalArgumentException(msg);
        }
        return value;
    }

    private static <T extends Comparable<T>> int compareTo(T value, T requirement) {
        notNull(value, "value cannot be null.");
        notNull(requirement, "requirement cannot be null.");
        return value.compareTo(requirement);
    }

    /**
     * Asserts that a specified {@code value} is greater than the given {@code requirement}, throwing
     * an {@link IllegalArgumentException} with the given message if not.
     *
     * @param <T>         the type of value to check and return if the requirement is met
     * @param value       the value to check
     * @param requirement the requirement that {@code value} must be greater than
     * @param msg         the message to use for the {@code IllegalArgumentException} if thrown.
     * @return {@code value} if greater than the specified {@code requirement}.
     * @since 0.12.0
     */
    public static <T extends Comparable<T>> T gt(T value, T requirement, String msg) {
        if (compareTo(value, requirement) <= 0) {
            throw new IllegalArgumentException(msg);
        }
        return value;
    }

    public static <T extends Comparable<T>> T gte(T value, T requirement, String msg) {
        if (compareTo(value, requirement) < 0) {
            throw new IllegalArgumentException(msg);
        }
        return value;
    }

    /**
     * Asserts that a specified {@code value} is less than or equal to the given {@code requirement}, throwing
     * an {@link IllegalArgumentException} with the given message if not.
     *
     * @param <T>         the type of value to check and return if the requirement is met
     * @param value       the value to check
     * @param requirement the requirement that {@code value} must be greater than
     * @param msg         the message to use for the {@code IllegalArgumentException} if thrown.
     * @return {@code value} if greater than the specified {@code requirement}.
     * @since 0.12.0
     */
    public static <T extends Comparable<T>> T lte(T value, T requirement, String msg) {
        if (compareTo(value, requirement) > 0) {
            throw new IllegalArgumentException(msg);
        }
        return value;
    }


    /**
     * Asserts that the specified {@code value} is not null, otherwise throws an
     * {@link IllegalStateException} with the specified {@code msg}.  Intended to be used with
     * code invariants (as opposed to method arguments, like {@link #notNull(Object, String)}).
     *
     * @param value value to assert is not null
     * @param msg   exception message to use if {@code value} is null
     * @param <T>   value type
     * @return the non-null value
     * @throws IllegalStateException with the specified {@code msg} if {@code value} is null.
     */
    public static <T> T stateNotNull(T value, String msg) throws IllegalStateException {
        if (value == null) {
            throw new IllegalStateException(msg);
        }
        return value;
    }
}
