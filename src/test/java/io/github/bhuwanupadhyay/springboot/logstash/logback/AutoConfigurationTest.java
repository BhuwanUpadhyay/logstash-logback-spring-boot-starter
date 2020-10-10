package io.github.bhuwanupadhyay.springboot.logstash.logback;

import net.logstash.logback.appender.LogstashTcpSocketAppender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AutoConfigurationTest {

  @Test
  void testDefaultContexts() {
    ConfigurableApplicationContext context =
        new SpringApplicationBuilder(SpringLogstashLogbackAutoConfiguration.class)
            .run();

    assertThat(context.getBean(LogstashTcpSocketAppender.class)).isNotNull();
  }

  @Test
  void testContextsWhenEnabled() {
    ConfigurableApplicationContext context =
        new SpringApplicationBuilder(SpringLogstashLogbackAutoConfiguration.class)
            .properties("boot.logstash.enabled=true")
            .run();

    assertThat(context.getBean(LogstashTcpSocketAppender.class)).isNotNull();
  }

  @Test
  void testContextsWhenDisabled() {
    ConfigurableApplicationContext context =
        new SpringApplicationBuilder(SpringLogstashLogbackAutoConfiguration.class)
            .properties("boot.logstash.enabled=false")
            .run();

    assertThatThrownBy(() -> context.getBean(LogstashTcpSocketAppender.class))
        .isInstanceOf(NoSuchBeanDefinitionException.class);
  }

  @Test
  void testProperties() {
    SpringLogstashLogbackAutoConfiguration configuration = null;
    try {
      ConfigurableApplicationContext context =
          new SpringApplicationBuilder()
              .profiles("prop")
              .sources(PropertiesApp.class)
              .run();
      configuration = context.getBean(SpringLogstashLogbackAutoConfiguration.class);
    } catch (Exception ignored) {
    } finally {
      assertThat(configuration).isNotNull();
      assertThat(configuration.getDestination()).isEqualTo("elk:8080");
      assertThat(configuration.getName()).isEqualTo("hello");
      assertThat(configuration.getKeyStoreLocation()).isEqualTo("tmp/trust.pk");
      assertThat(configuration.getKeyStorePassword()).isEqualTo("1234");
    }
  }
}