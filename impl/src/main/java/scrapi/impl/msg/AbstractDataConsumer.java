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
package scrapi.impl.msg;

import scrapi.DataConsumer;
import scrapi.util.Assert;

import java.nio.ByteBuffer;

abstract class AbstractDataConsumer<T extends DataConsumer<T>> implements DataConsumer<T> {

    @SuppressWarnings("unchecked")
    protected final T self() {
        return (T) this;
    }

    protected abstract void doApply(byte input);

    @Override
    public final T apply(byte input) {
        doApply(input);
        return self();
    }

    protected abstract void doApply(byte[] input);

    public final T apply(byte[] input) {
        doApply(input);
        return self();
    }

    protected abstract void doApply(byte[] input, int offset, int len);

    @Override
    public final T apply(byte[] input, int offset, int len) {
        Assert.notNull(input, "input byte array cannot be null.");
        doApply(input, offset, len);
        return self();
    }

    protected abstract void doApply(ByteBuffer input);

    @Override
    public final T apply(ByteBuffer input) {
        Assert.notNull(input, "ByteBuffer input cannot be null.");
        doApply(input);
        return self();
    }
}
