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

/**
 * An {@code OctetKey} is a {@code SecretKey} where the key material is a single byte array value.
 */
public interface OctetKey extends SecretKey<javax.crypto.SecretKey> {

    //TODO: Ed448 and ED25519 keys can be considered 'octet' keys as well, even though they are public and private keys
    // perhaps we should change OctetKey to be an interface that extends from Key?
    // And then this interface would be renamed to OctetSecretKey?
    // Hrm - but the Ed448 and Ed25519 PublicKeys would have 'x' properties, not 'octets' correct?
    // and Ed448 and Ed25519 PrivateKeys would have 'd' properties and not 'octets'?

    byte[] octets();

    interface Builder extends KeyBuilder<OctetKey, Builder> {

        Builder octets(byte[] octets);
    }
}
