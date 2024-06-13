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

import java.util.Collection;
import java.util.function.Predicate;

public interface Interval<C extends Comparable<C>> extends Predicate<C> {

    static <C extends Comparable<C>> Interval<C> of(C value) {
        return new EndpointInterval<>(value);
    }

    static <C extends Comparable<C>> Interval<C> of(C min, C max) {
        return new ClosedInterval<>(min, max);
    }

    static <C extends Comparable<C>> Interval<C> of(Collection<C> values) {
        return new CollectionInterval<>(values);
    }

    static <C extends Comparable<C>> Interval<C> gte(C min) {
        return new LeftClosedInterval<>(min);
    }

}
