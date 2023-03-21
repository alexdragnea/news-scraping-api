package net.dg.newsscrapingapi.repository;

import net.dg.newsscrapingapi.helper.ObjectMother;
import net.dg.newsscrapingapi.model.News;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NewsRepositoryTest {

  @Autowired private NewsRepository newsRepository;

  @Test
  void testGetNewsByTitle() {
    newsRepository.saveAll(ObjectMother.buildListNews());
    newsRepository.flush();

    Optional<News> existingNews =
        newsRepository.getNewsByTitle(ObjectMother.buildListNews().get(0).getTitle());

    Assertions.assertThat(existingNews.get().getTitle()).isEqualTo("Spring Starts Here");
  }
}
