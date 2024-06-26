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
package scrapi.key;

import scrapi.lang.SecurityException;

/**
 * General-purpose exception when encountering a problem with a cryptographic {@link Key}.
 *
 * @since SCRAPI_RELEASE_VERSION
 */
public class KeyException extends SecurityException {

    private static final long serialVersionUID = 5292618870619965505L;

    /**
     * Creates a new instance with the specified explanation message.
     *
     * @param message the message explaining why the exception is thrown.
     */
    public KeyException(String message) {
        super(message);
    }

    /**
     * Creates a new instance with the specified explanation message and underlying cause.
     *
     * @param msg   the message explaining why the exception is thrown.
     * @param cause the underlying cause that resulted in this exception being thrown.
     */
    public KeyException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
