package net.dg.newsscrapingapi.repository;

import java.util.List;
import java.util.Optional;
import net.dg.newsscrapingapi.helper.ObjectMother;
import net.dg.newsscrapingapi.model.News;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = NewsRepositoryTest.DataSourceInitializer.class)
@Profile("postgres")
class NewsRepositoryTest {

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

  @BeforeEach
  void setup() {
    newsRepository.saveAll(ObjectMother.buildListNews());
    newsRepository.flush();
  }

  @Test
  void testGetNewsByTitle() {

    Optional<News> existingNews =
        newsRepository.getNewsByTitle(ObjectMother.buildListNews().get(0).getTitle());

    Assertions.assertThat(existingNews.get().getTitle()).isEqualTo("Spring Starts Here");
  }

  @Test
  void testFindByKeyword() {

    List<News> existingNews = newsRepository.findByKeyword("iphone");

    Assertions.assertThat(existingNews.size()).isEqualTo(1);
  }
}
