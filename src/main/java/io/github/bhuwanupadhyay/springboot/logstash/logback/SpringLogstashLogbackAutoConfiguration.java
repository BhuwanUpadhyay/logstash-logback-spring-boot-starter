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
  private String keyStoreLocation;
  private String keyStorePassword;

  @Bean
  @ConditionalOnProperty(value = "boot.logstash.enabled", matchIfMissing = true)
  public LogstashTcpSocketAppender logstashAppender() {
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    LogstashTcpSocketAppender appender = new LogstashTcpSocketAppender();
    appender.setName(LOGSTASH_APPENDER_NAME);
    appender.setContext(loggerContext);
    appender.addDestination(destination);
    if (keyStoreLocation != null) {
      SSLConfiguration sslConfiguration = new SSLConfiguration();
      KeyStoreFactoryBean factory = new KeyStoreFactoryBean();
      factory.setLocation(keyStoreLocation);
      if (keyStorePassword != null) {
        factory.setPassword(keyStorePassword);
      }
      sslConfiguration.setTrustStore(factory);
      appender.setSsl(sslConfiguration);
    }
    LogstashEncoder encoder = new LogstashEncoder();
    encoder.setContext(loggerContext);
    encoder.setIncludeContext(true);
    encoder.setCustomFields("{\"applicationName\":\"" + name + "\"}");
    encoder.start();
    appender.setEncoder(encoder);
    appender.start();
    loggerContext.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(appender);
    return appender;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public String getKeyStoreLocation() {
    return keyStoreLocation;
  }

  public void setKeyStoreLocation(String keyStoreLocation) {
    this.keyStoreLocation = keyStoreLocation;
  }

  public String getKeyStorePassword() {
    return keyStorePassword;
  }

  public void setKeyStorePassword(String keyStorePassword) {
    this.keyStorePassword = keyStorePassword;
  }

  public String getName() {
    return name;
  }
}
