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
package scrapi.impl.util;

import scrapi.impl.lang.Assertion;
import scrapi.util.Assert;

import java.util.Objects;
import java.util.function.Predicate;

public class DefaultParameter<T> implements Parameter<T> {

    private final String ID;
    private final String NAME;
    private final boolean SECRET;
    private final Assertion<T> assertion;

    public DefaultParameter(String id, String name, boolean secret, Predicate<T> requirement, String exMsgSuffix) {
        this.ID = Assert.hasText(id, "id cannot be null or empty");
        this.NAME = Assert.hasText(name, "name cannot be null or empty");
        this.SECRET = secret;
        String exMsg = this + exMsgSuffix;
        this.assertion =
                Assertion.<T>notNull(this + " must not be null.")
                        .and(Assertion.of(requirement, exMsg));
    }

    @Override
    public String name() {
        return this.NAME;
    }

    @Override
    public boolean isSecret() {
        return this.SECRET;
    }

    @Override
    public String id() {
        return this.ID;
    }

    @Override
    public T check(T value) throws IllegalArgumentException {
        return this.assertion.check(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, NAME, SECRET);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof DefaultParameter)) return false;
        DefaultParameter<?> other = (DefaultParameter<?>) obj;
        return this.ID.equals(other.ID) && this.NAME.equals(other.NAME) && this.SECRET == other.SECRET;
    }

    @Override
    public String toString() {
        return "'" + this.ID + "' (" + this.NAME + ")";
    }
}
