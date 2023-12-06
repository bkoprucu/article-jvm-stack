## JVM Memory Filler

### Description

Spawns threads and fills stack and heap memory for memory allocation analysis of JVM.

Written for the article https://berksoftware.com/23/12/JVM-Stack-Memory.

### Build

Switch to the JDK version to analyze:

```shell
$ sdk use java 11.0.21-amzn
```

Build and install Docker image tagged with current Java version:
```shell
$ ./mvnw install
```
Docker image tagged with current Java version will be installed: **memoryfiller:java11**

An image for older version then current one can be build with Maven property `jvm_version`:
```shell
$ ./mvnw -Djvm_version=17
```

Java 8 version is in separate branch: [java8](https://github.com/bkoprucu/article-jvm-stack/tree/java8)

### Usage

Refer to the article https://berksoftware.com/23/12/JVM-Stack-Memory
