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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NewsRepositoryTest {

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
