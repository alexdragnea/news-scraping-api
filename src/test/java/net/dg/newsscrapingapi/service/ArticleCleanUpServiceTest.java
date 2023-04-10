package net.dg.newsscrapingapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = ArticleCleanUpServiceTest.DataSourceInitializer.class)
@ComponentScan(basePackages = "net.dg.newsscrapingapi.service")
public class ArticleCleanUpServiceTest {

  @Autowired private ArticleCleanUpService articleCleanUpService;

  @Container
  private static final PostgreSQLContainer<?> database =
      new PostgreSQLContainer<>("postgres:12.9-alpine");

  public static class DataSourceInitializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
          applicationContext,
          "spring.datasource.url=" + database.getJdbcUrl(),
          "spring.datasource.username=" + database.getUsername(),
          "spring.datasource.password=" + database.getPassword());
    }
  }

  @Autowired private NewsRepository newsRepository;

  @Test
  public void testCleanUpArticles() {
    News news1 = new News();
    news1.setTitle("Test Article 1");
    news1.setUrl("http://example.com/test1");
    newsRepository.save(news1);

    News news2 = new News();
    news2.setTitle("Test Article 2");
    news2.setUrl("http://example.com/test2");
    newsRepository.save(news2);

    articleCleanUpService.removeNonExistentArticles();

    List<News> newsList = newsRepository.findAll();
    assertEquals(0, newsList.size());
  }
}
