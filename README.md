<!--
  ~ Copyright © 2023 Les Hazlewood
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  -->
# scrapi: `S`imple `CR`yptography `API`

scrapi (pronounced "scrappy") attempts to be the easiest to use and understand cryptography API for the JVM. It is
intended to be a simpler - but just as feature rich - alternative to the JCA 
([Java Cryptography Architecture](https://docs.oracle.com/en/java/javase/21/security/preface.html)) APIs.

## Background

`scrapi` exists as an answer to one question:

> What would Java security APIs look like if they were designed in 2024?

The JCA (and former JCE - Java Cryptography Extension) APIs were designed and built in a very different era in Java's 
history, before the strong advent of design patterns, generics, and functional interfaces.  As a result, they largely:

* are heavily procedural, and not very Object-Oriented
* often ignore type-safety, using string names and concatenations to initialize objects instead of concrete data types
* avoid generics and their associated conveniences
* use heavyweight checked exceptions that are unnecessarily forceful and cumbersome
* prefer concrete and abstract types instead of interface-driven design, making for difficult-to-maintain implementations
* do not leverage JDK 8+ functional paradigms
* do not often leverage design patterns (like builders, visitors, etc)
* sometimes allow insecure usage patterns unless one is more deeply familiar with cryptographic concepts.

This is not to fault the JVM team at all however, their hands are largely tied for historical and legacy support 
reasons.

## Goals

Scrapi aims to leapfrog these historical limitations in hopes of providing a modern, clean, and elegant security
and cryptography API for the JVM platform.  Scrapi's goals are to be:

* Secure By Default: erroneous or insecure API usage should not be possible or rejected unless explicitly requested 
or overridden by an application developer. An application developer should not need to be a security expert requiring 
nuanced understanding of cryptographic algorithms to be able to use them safely.


* Object-Oriented and less procedural.


* Interface-driven: the large majority of APIs used by application developers should be interfaces, using builders
  and factory design patterns to eliminate tight coupling to implemenations.


* Generic: leverage JDK 7+ generics where feasible.


* Functional: leverage JDK 8+ functional paradigms where appropriate.


* Runtime exception based: to allow application developers to determine if or when they wish to catch exceptions.


* [Fluent](https://en.wikipedia.org/wiki/Fluent_interface): use method chaining and builders where appropriate to help
reduce lines of code written by application developers.


* A JEP ([JDK Enhancement Proposal](https://en.wikipedia.org/wiki/JDK_Enhancement_Proposal)) to become a new official
JDK API if warranted and desired by the Java and JEP community.


## Non-Goals

It is an explicit design goal to _not_ replace the JCA APIs, and instead 'sit on top of' or 'delegate' to them when
feasible.  This ensures Scrapi can be leveraged quickly and conveniently in existing JDK applications, as well as
with existing JCA Provider and HSM (Hardware Security Module) implementations, like 
[BouncyCastle](https://www.bouncycastle.org/java.html).

It is better to 'sit on the shoulders of giants' instead of attempt to remove or replace them.

## Status

This project is experimental and still in initial design stages.  There are no releases yet.

## Design Tenets

### Standard Taxonomy

Cryptographic primitives have a standard taxonomy used by cryptographers and mathematicians around the world, and it's
important to retain that taxonomy (and name Java classes) accurately so APIs are as intuitive (and 
semantically correct) where possible.  The existing JCA diverges from this philosophy enough, and that often causes 
problems and confusion for application developers.

For example, `java.security.Signature` is _not_ actually a cryptographic signature. Instead, that class represents
behaviors for an _algorithm_ implementation that can be used to produce or verify signatures.

Similarly, `java.security.MessageDigest` is not actually a cryptographic digest.  It represents algorithm operations
that eventually _produce_ an actual digest.

Conversely, scrapi has `Algorithm` implementations that do produce actual `Digest` instances, and like good OO design,
those `Digest` instances can be inspected and interacted with as the primitives they represent.

### Polymorphism

A sufficient number of JCA classes (such as `java.security.Signature`, `javax.crypto.Mac`, 
`java.security.MessageDigest`) support base identical behaviors (consume some bytes, produce a result, or verify
a previous result), but their APIs are completely different, they don't use the same (or parent) interface behaviors
to ensure API consistency.  Similar divergences occur with , or `java.security.interfaces.XECKey` and
`java.security.interfaces.EdECKey`, etc.

An argument could be made that, by having different APIs that do the nearly identical things, the original JCA 
designers purposefully prevented polymorphic use to ensure application developers cannot accidentally use one where 
another should be used (which otherwise could weaken security).  Instead, scrapi favors 
[SOLID design principles](https://en.wikipedia.org/wiki/SOLID), relying on 
other forms of API usage assertions (e.g. throwing an exception if an API is used incorrectly).  This still affords 
security while having a cleaner, easier to maintain and understand API that is more readable and usable for 
developers of all experience levels.

### Abandoning JavaBeans Naming Conventions

In October of 1996, the [JavaBeans Specification, section 8.3](https://download.oracle.com/otndocs/jcp/7224-javabeans-1.01-fr-spec-oth-JSpec/) created getter and setter naming conventions (e.g. `getSomething()`, `setSomething(String something)`) to help dynamically determine which properties of an object were readable and writable/mutable at runtime, mostly to facilitate AWT/Swing user-interface and tool development. Many projects, API specifications and open-source libraries realized this was valuable for non-UI code as well, and proliferated that convention for the same benefits.

However, since the advent of annotations introduced in Java 1.5 in [late 2004](https://en.wikipedia.org/wiki/Java_version_history#Java_5), this convention hasn't been necessary. This is because Java annotations can be used with far more power and introspected at compile time (instead of runtime method name inspection), to indicate which properties support reading and writing, as well as any other number of behaviors or metadata that could be useful (e.g. [JPA annotations](https://javadoc.io/static/javax.persistence/javax.persistence-api/2.2/javax/persistence/package-summary.html) are one such example).

Consequently, scrapi avoids the use of `get` and `set` prefixes, relying on the inherent Java language model that already indicates readability and writability by the nature of a `public` method having arguments or not.  For example:

```java
public interface Example {
    
    String id(); // public without arguments, so it's readable

    void id(String id); // public with arguments, so it's writable 
}
```

This decision was made for a few reasons:

1. Code is more readable and less verbose, especially when method chaining
2. scrapi is primarily designed for application developers authoring code manually, not for user interfaces or external tool automation.
3. If any such metadata is required beyond the intrinsic Java language model features for public readability and writability or tool automation, we'll use annotations as necessary for better compile-time and runtime capabilities.

### Lifecycle API Separation

In many JCA APIs, object instances have lifecycle management methods combined with usage methods.  Calling them out of
order will always produce exceptions.  For example, using `javax.crypto.Mac`, this is possible at compile time:

```java
Mac mac = Mac.getInstance("HmacSHA256");
mac.update(aByteArray);
mac.init(aSecretKey);
```
but clearly this code would fail at runtime because a `Mac` instance must be initialized with a `SecretKey` first
before data can be consumed for mac calculation.  Alternatively, an instance cannot be `init`ialized after data is
consumed.

Similarly, for a `java.security.Signature`:

```java
import java.security.Signature;

Signature sig = Signature.getInstance("SHA256withRSA");
sig.update(aByteArray);
sig.initVerify(aPublicKey);
sig.initSign(aPrivateKey);
```

Notice that signing and verification methods also exist on the same entity, when only one of the two may be used.

Ideally, the two code examples above _should not even be possible_ because they are always wrong.

Scrapi instead separates instance creation and initialization APIs from instance usage APIs.  Once an object is 
created and initialized, its API can only support operations that are 'legal' after creation.

For example, scrapi has a `SignatureAlgorithm` concept which can be configured, and that produces a `Signer` instance
that can only be used to sign data, or a `Verifier` instance that can only be used to verify data.  It is not possible
to compile code with invalid API usages.  For example:

```java
SignatureAlgorithm alg = Digests.RS256; // SHA256WithRSA
Signer signer = alg.key(aPrivateKey); // 'signer' is fully initialized and can _only_ be used to sign data
Verifier<?> verifier = alg.key(aPublicKey) // 'verifier' can _only_ be used to verify a data signature
```


### Keys

#### Encoding/Decoding

Unlike `java.security.Key`, `scrapi.key.Key` instances do not have `getFormat()` or `getEncoded()` methods; formatting
and encoding/decoding key material is an orthogonal concern to key usage and such concepts should not be
tightly coupled to a `Key` concept.

Consequently, key encoders/decoders should exist to handle such concerns.

Additionally, also unlike `java.security.Key`, `scrapi.key.Key` instances should not extend `java.io.Serializable`, which 
imposes an often-unnecessary implementation burden that many (most?) applications never need as long as the 
aforementioned Key encoders/decoders exist.  PEM, DER, and JWK formats are better serialization mechanisms as they are 
IANA/IETF global standards and not Java-specific.  Custom (even Java-specific) serializations can be implemented with 
custom encoders/decoders if desired.

#### Size/Length

Security keys are often validated by determining a key's size/length in bits, and then asserting if the size is
strong enough for a given cryptographic algorithms.

`java.security.Key` does not provide a polymorphic way to discover a key's size/length in bits, instead forcing
Java developers to engage in messy if-then-else conditionals.  For example:

```java
if (key instanceof SecretKey) {
    SecretKey sk = (SecretKey)key;
    String format = sk.getFormat();
    if ("RAW".equals(format)) {
        byte[] encoded = sk.getEncoded();
        if (encoded != null) {
            size = (encoded.length * 8);
            Arrays.fill(encoded, (byte)0)
        }
    }
} else if (key instanceof RSAKey) {
    RSAKey pubk = (RSAKey)key;
    size = pubk.getModulus().bitLength();
} else if (key instanceof ECKey) {
    ECKey pubk = (ECKey)key;
    size = pubk.getParams().getOrder().bitLength(); 
} else if (key instanceof DSAKey) {
    // ... etc ...
```

This both requires developers to know which specific key properties reflect size as well as how to extract it, and 
unnecessarily forces duplicate logic across any codebase that needs to perform similar behavior.

Additionally, even if a key's encoded bytes are not available (e.g. external in an HSM), various HSMs still are able
to supply length metadata even without supplying the key material/encoded bytes, but `java.security.Key` and its
sub-interfaces do not support such introspection.

Consequently, all `scrapi.key.Key` instances have a `Optional<Integer> bitLength()` method to ensure key 
size can be represented if possible, even if they key material may not be present.

#### Public Key derivation

`java.security.PrivateKey` instances have no capability to obtain or derive their corresponding public key, but this
ability is inherent and implicit in all private key concepts, and should be supported as such.  For example:

```java
aPrivateKey.publicKey();
```

Even if a private key's material is not available (e.g. it resides externally from the JVM in an HSM), its associated 
public key is still valuable and often necessary, especially when validating untrusted material, such as within key 
exchange algorithms.

Having this capability may even make the concept of a key `Pair` extraneous and unnecessary.

### Key Pairs

`java.security.KeyPair` is a non-generic concrete class, so you never know what type of keys it contains, which is always frustrating when you need to cast its contained keys to the types you need (and hope that they really are those types, or perform a bunch of type and/or algorithm name checking to see if they are).

Scrapi in contrast does not have a `KeyPair` concept because all scrapi private keys are able to access their associated public key via `privateKey.publicKey()`. And because all Scrapi keys use generics, `publicKey()` will always return the specific subtype associated with the scrapi 
private key's subtype.

In a sense then, scrapi `PrivateKey` instances can be thought of as 'key pairs' from a JCA perspective. Even so,
if you still need to use the legacy JCA `KeyPair` API, you can obtain one from the scrapi `PrivateKey`, e.g.

```java
java.security.KeyPair pair = scrapiPrivateKey.toJcaKeyPair();
```

### Builders

The JCA `KeyFactory`, `KeyGenerator` and `KeyPairGenerator` concepts are independent and often confusing to 
application developers when one vs the other should be used. Fluent and intuitive `Builder` concepts could likely 
replace both concepts.

### RSA Private Keys

The JCA has two `RSAPrivateKey` sub-interfaces that are peers:

```
RSAPrivateKey
|-- RSAPrivateCrtKey
|-- RSAMultiPrimePrivateCrtKey
```

With numerous problems:

- The two interfaces are _identical_ - with identically named methods - except for 
one extra method in `RSAMultiPrimePrivateCrtKey`, _but they don't share a common interface_.

- This means they're not polymorphic, and implementations cannot rely on common logic. Anything that inspects or 
  interacts with either type must duplicate logic.

- The naming is inconsistent; subtypes should retain parent type names and prefix additional meaning/context for 
  obvious concept aggregation and readability.

The original JDK naming and hierarchy could/should have been:
```
RSAPrivateKey
|-- CrtRSAPrivateKey
  |-- MultiPrimeCrtRSAPrivateKey
```

but it's not, causing confusion.

Additionally, [RFC 8017, Section 3.2.2](https://datatracker.ietf.org/doc/html/rfc8017#section-3.2) is explicit that the extra 'multi primes' are purely optional, and this 
could have easily been modeled in Java as `RSAPrivateKey` with one sub-interface `CrtRSAPrivateKey` where the
latter has an empty collection if addtional r >= 3 primes are not necessary. A separate peer / non-polymorphic type 
is not needed at all when a simple empty collection check would suffice.

Consequently, scrapi adopts the simpler, polymorphic, and intuitive alternative to support when RSA multi-prime private keys may be used:

```
RsaPrivateKey
|-- CrtRsaPrivateKey
```
