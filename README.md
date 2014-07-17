Spearal JAX-RS
==============

## What is Spearal?

Spearal is a compact binary format for exchanging arbitrary complex data between various endpoints such as Java EE, JavaScript / HTML, Android and iOS applications.

Spearal-JaxRS is an extension of Spearal-Java which implements the necessary body reader/writer providers to use Spearal in JAX-RS applications.

## How to get and build the project?

First, you need to get, build and install Spearal-Java:

````sh
$ git clone https://github.com/spearal/spearal-java.git
$ cd spearal-java
$ ./gradlew install
````

Then, you can build Spearal JAX-RS:

````sh
$ cd ..
$ git clone https://github.com/spearal/spearal-jaxrs.git
$ cd spearal-jaxrs
$ ./gradlew build
````

The built library can then be found in the `build/libs/` directory.

## How to use the library?

TODO
