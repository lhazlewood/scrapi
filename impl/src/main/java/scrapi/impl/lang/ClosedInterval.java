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

class ClosedInterval<C extends Comparable<C>> implements Interval<C> {

    private final C min;
    private final C max;

    protected ClosedInterval(C min, C max) {
        this.min = Assert.notNull(min, "min cannot be null.");
        this.max = Assert.notNull(max, "max cannot be null.");
        Assert.gt(max, min, "Max value must be larger than Min value.");
    }

    @Override
    public boolean test(C c) {
        return c != null && min.compareTo(c) <= 0 && this.max.compareTo(c) >= 0;
    }

    @Override
    public String toString() {
        return ">= " + this.min + " and <= " + this.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClosedInterval)) return false;
        ClosedInterval<?> that = (ClosedInterval<?>) o;
        return this.min.equals(that.min) && this.max.equals(that.max);
    }
}
