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
package scrapi.impl.key;

import scrapi.impl.jca.AlgorithmSupport;
import scrapi.key.Key;
import scrapi.key.KeyBuilder;

import java.security.Provider;

abstract class AbstractKeyBuilder<K extends Key<?>, T extends KeyBuilder<K, T>>
        extends AlgorithmSupport<T>
        implements KeyBuilder<K, T> {

    protected Provider provider;

    protected final int minSize;
    protected int size = 0; // zero means not configured

    protected static int assertByteable(int size, String name) {
        if (size <= 0) {
            String msg = name + " must be greater than 0. Value: " + size;
            throw new IllegalArgumentException(msg);
        }
        if (size % Byte.SIZE != 0) {
            String msg = name + " must be a multiple of " + Byte.SIZE + ". Size: " + size;
            throw new IllegalArgumentException(msg);
        }
        return size;
    }

    public AbstractKeyBuilder(String jcaName, int minSize) {
        super(jcaName);
        this.minSize = assertByteable(minSize, "minSize");
    }

    protected static int assertSize(int size, int minSize, String name) {
        assertByteable(size, name);
        if (size < minSize) {
            String msg = name + " must be greater than or equal to " + minSize + ". Size: " + size;
            throw new IllegalArgumentException(msg);
        }
        return size;
    }

    @Override
    public T size(int size) {
        this.size = assertSize(size, minSize, "size");
        return self();
    }
}
