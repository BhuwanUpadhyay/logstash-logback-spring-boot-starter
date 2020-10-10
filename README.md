# Logstash Logging in Spring Boot [![Twitter](https://img.shields.io/twitter/follow/IAmVuwan.svg?style=social&logo=twitter&label=Follow%20Me)](https://twitter.com/IAmVuwan)



[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.bhuwanupadhyay/logstash-logback-spring-boot-starter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.bhuwanupadhyay/logstash-logback-spring-boot-starter)
[![Github Action](https://img.shields.io/github/workflow/status/BhuwanUpadhyay/logstash-logback-spring-boot-starter/Build)](https://github.com/BhuwanUpadhyay/logstash-logback-spring-boot-starter/actions?query=workflow%3ABuild)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=BhuwanUpadhyay_logstash-logback-spring-boot-starter&metric=alert_status)](https://sonarcloud.io/dashboard?id=BhuwanUpadhyay_logstash-logback-spring-boot-starter)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=BhuwanUpadhyay_logstash-logback-spring-boot-starter&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=BhuwanUpadhyay_logstash-logback-spring-boot-starter)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=BhuwanUpadhyay_logstash-logback-spring-boot-starter&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=BhuwanUpadhyay_logstash-logback-spring-boot-starter)

## Usages

This library helps to send logs automatically to Logstash.

## Features
The main feature provided by `logstash-logback-spring-boot-starter`: 
- automatically configured the spring boot application to send logs to Logstash.

## Getting started          

Available on [Maven Central](https://mvnrepository.com/artifact/io.github.bhuwanupadhyay/logstash-logback-spring-boot-starter).

```
<dependency>
  <groupId>io.github.bhuwanupadhyay</groupId>
  <artifactId>logstash-logback-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```

Auto-configuration enable logstash logging automatically, and connected with logstash server url `localhost:5044`.

In case if you have to change logstash server url, override following properties:

```yaml
boot.logstash:
  url: elk:4100
```

To disable logstash logging, override following properties:

```yaml
boot.logstash:
  enabled: false
```

All configuration properties:

```yaml
boot.logstash:
  enabled: true
  url: elk:4100
  key-store-location: keystore/trust.pk
  key-store-password: 12345  
``` 

Thank you!