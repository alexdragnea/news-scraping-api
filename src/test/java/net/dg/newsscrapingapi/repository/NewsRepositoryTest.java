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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NewsRepositoryTest {

  @Container
  static PostgreSQLContainer postgresqlContainer =
      new PostgreSQLContainer("postgres:11.1")
          .withDatabaseName("scraper")
          .withUsername("postgres")
          .withPassword("admin");

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresqlContainer::getUsername);
    registry.add("spring.datasource.password", postgresqlContainer::getPassword);
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
