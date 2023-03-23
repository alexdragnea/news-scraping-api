package net.dg.newsscrapingapi.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class NewsServiceConfig {

  @Bean
  public List<String> urls() {
    return Arrays.asList("https://example.com, httpȘ//");
  }
}
