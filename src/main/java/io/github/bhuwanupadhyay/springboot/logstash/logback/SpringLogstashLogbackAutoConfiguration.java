package io.github.bhuwanupadhyay.springboot.logstash.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.net.ssl.KeyStoreFactoryBean;
import ch.qos.logback.core.net.ssl.SSLConfiguration;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "boot.logstash")
public class SpringLogstashLogbackAutoConfiguration {

  private static final String LOGSTASH_APPENDER_NAME = "LOGSTASH";
  @Value("${spring.application.name:-}")
  private String name;
  private String destination = "localhost:5044";
  private String trustStoreLocation;
  private String trustStorePassword;

  @Bean
  @ConditionalOnProperty("boot.logstash.enabled")
  public LogstashTcpSocketAppender logstashAppender() {
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    LogstashTcpSocketAppender logstashTcpSocketAppender = new LogstashTcpSocketAppender();
    logstashTcpSocketAppender.setName(LOGSTASH_APPENDER_NAME);
    logstashTcpSocketAppender.setContext(loggerContext);
    logstashTcpSocketAppender.addDestination(destination);
    if (trustStoreLocation != null) {
      SSLConfiguration sslConfiguration = new SSLConfiguration();
      KeyStoreFactoryBean factory = new KeyStoreFactoryBean();
      factory.setLocation(trustStoreLocation);
      if (trustStorePassword != null) {
        factory.setPassword(trustStorePassword);
      }
      sslConfiguration.setTrustStore(factory);
      logstashTcpSocketAppender.setSsl(sslConfiguration);
    }
    LogstashEncoder encoder = new LogstashEncoder();
    encoder.setContext(loggerContext);
    encoder.setIncludeContext(true);
    encoder.setCustomFields("{\"applicationName\":\"" + name + "\"}");
    encoder.start();
    logstashTcpSocketAppender.setEncoder(encoder);
    logstashTcpSocketAppender.start();
    loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(logstashTcpSocketAppender);
    return logstashTcpSocketAppender;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getTrustStoreLocation() {
    return trustStoreLocation;
  }

  public void setTrustStoreLocation(String trustStoreLocation) {
    this.trustStoreLocation = trustStoreLocation;
  }

  public String getTrustStorePassword() {
    return trustStorePassword;
  }

  public void setTrustStorePassword(String trustStorePassword) {
    this.trustStorePassword = trustStorePassword;
  }

  public String getName() {
    return name;
  }
}
