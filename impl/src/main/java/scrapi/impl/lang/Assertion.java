/*
 * Copyright © 2024 Les Hazlewood
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
package scrapi.impl.lang;

import scrapi.util.Assert;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

@FunctionalInterface
public interface Assertion<T> {

    T check(T value);

    static <T> Assertion<T> of(Predicate<T> req, Supplier<String> msg) {
        return new DefaultAssertion<>(req, msg);
    }

    static <T> Assertion<T> of(Predicate<T> req, String msg) {
        return new DefaultAssertion<>(req, msg);
    }

    static <T> Assertion<T> notNull(final String msg) {
        return of(Objects::nonNull, msg);
    }

    default Assertion<T> and(Assertion<T> other) {
        Assert.notNull(other, "other assertion cannot be null.");
        return t -> other.check(check(t));
    }
}
