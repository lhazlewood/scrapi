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

class EndpointInterval<C extends Comparable<C>> implements Interval<C> {

    protected final C endpoint;

    EndpointInterval(C endpoint) {
        this.endpoint = Assert.notNull(endpoint, "endpoint cannot be null.");
    }

    @Override
    public int hashCode() {
        return this.endpoint.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof EndpointInterval)) return false;
        return getClass().equals(o.getClass()) && this.endpoint.equals(((EndpointInterval<?>) o).endpoint);
    }

    @Override
    public String toString() {
        return "equal to " + this.endpoint;
    }

    @Override
    public boolean test(C c) {
        return c != null && this.endpoint.compareTo(c) == 0;
    }
}
