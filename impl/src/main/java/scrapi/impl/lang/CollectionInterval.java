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
import scrapi.util.Collections;
import scrapi.util.Strings;

import java.util.Collection;

public class CollectionInterval<C extends Comparable<C>> implements Interval<C> {

    private final Collection<? extends C> values;
    private final String TO_STRING_VAL;

    public CollectionInterval(Collection<? extends C> values) {
        this.values = Assert.notEmpty(values, "values cannot be null or empty.");
        this.TO_STRING_VAL = "equal to one of " + Collections.toDelimitedString(values, Strings.COMMA, "{", "}");
    }

    @Override
    public int hashCode() {
        return this.values.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CollectionInterval)) return false;
        return this.values.equals(((CollectionInterval<?>) o).values);
    }

    @Override
    public String toString() {
        return TO_STRING_VAL;
    }

    @Override
    public boolean test(C c) {
        if (c == null) return false;
        for (C val : this.values) {
            if (val.compareTo(c) == 0) return true;
        }
        return false;
    }
}
