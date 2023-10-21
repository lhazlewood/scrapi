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
