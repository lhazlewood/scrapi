/*
 * Copyright © 2023 Les Hazlewood
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

import java.util.Objects;

class DefaultPair<A, B> implements Pair<A, B> {

    private final A a;
    private final B b;

    DefaultPair(A a, B b) {
        this.a = Objects.requireNonNull(a, "First argument cannot be null.");
        this.b = Objects.requireNonNull(b, "Second argument cannot be null.");
    }

    @Override
    public A getA() {
        return this.a;
    }

    @Override
    public B getB() {
        return this.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.a, this.b);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Pair) {
            Pair<?, ?> other = (Pair<?, ?>) obj;
            return this.a.equals(other.getA()) && this.b.equals(other.getB());
        }
        return false;
    }

    @Override
    public String toString() {
        return "{a: " + this.a + ", b: " + this.b + "}";
    }
}
