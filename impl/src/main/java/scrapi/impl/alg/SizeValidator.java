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
package scrapi.impl.alg;

import scrapi.alg.Size;
import scrapi.util.Assert;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class SizeValidator implements UnaryOperator<Size> {

    private final String name;
    private final Predicate<Size> valid;

    public SizeValidator(String name, Predicate<Size> i) {
        this.name = Assert.hasText(name, "name must not be null or empty.");
        this.valid = Assert.notNull(i, "interval must not be null.");
    }

    @Override
    public Size apply(Size size) throws IllegalArgumentException {
        if (size == null) {
            String msg = name + " must not be null.";
            throw new IllegalArgumentException(msg);
        }
        if (size.bits() % Byte.SIZE != 0) {
            String msg = name + " value '" + size + "' must be a multiple of " + Byte.SIZE + ".";
            throw new IllegalArgumentException(msg);
        }
        if (!valid.test(size)) {
            String msg = "Invalid " + name + " value '" + size + "': must be " + valid + ".";
            throw new IllegalArgumentException(msg);
        }
        return size;
    }
}
