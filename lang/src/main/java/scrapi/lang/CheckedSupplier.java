/*
 * Copyright © 2024 Vavr (http://vavr.io) and Les Hazlewood
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
package scrapi.lang;

import java.util.function.Supplier;

/**
 * A {@link Supplier}-like interface which allows throwing Throwable.
 */
@FunctionalInterface
public interface CheckedSupplier<T> {

    T get() throws Throwable;

    default Supplier<T> unchecked() {
        return () -> {
            try {
                return get();
            } catch (Throwable t) {
                return sneakyThrow(t);
            }
        };
    }

    @SuppressWarnings("unchecked")
    static <T extends Throwable, R> R sneakyThrow(Throwable t) throws T {
        throw (T) t;
    }
}
