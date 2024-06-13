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

import java.util.function.Predicate;
import java.util.function.Supplier;

public class DefaultAssertion<T> implements Assertion<T> {

    private final Predicate<T> requirement;
    private final Supplier<String> msg;

    private static Supplier<String> msg(String msg) {
        Assert.hasText(msg, "msg cannot be null or empty");
        return () -> msg;
    }

    public DefaultAssertion(Predicate<T> requirement, String exMsg) {
        this(requirement, msg(exMsg));
    }

    public DefaultAssertion(Predicate<T> requirement, Supplier<String> msg) {
        this.requirement = Assert.notNull(requirement, "requirement cannot be null");
        this.msg = Assert.notNull(msg, "msg cannot be null");
    }

    @Override
    public T check(T value) throws IllegalArgumentException {
        if (!requirement.test(value)) {
            String msg = this.msg.get();
            throw new IllegalArgumentException(msg);
        }
        return value;
    }
}
