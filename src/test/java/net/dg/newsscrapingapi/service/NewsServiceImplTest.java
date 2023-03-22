package net.dg.newsscrapingapi.service;

import net.dg.newsscrapingapi.helper.ObjectMother;
import net.dg.newsscrapingapi.model.ResponseBody;
import net.dg.newsscrapingapi.repository.NewsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NewsServiceImplTest {

  @MockBean NewsServiceImpl newsService;
  @Mock NewsRepository newsRepository;

  @Test
  void testGetNewsOrder() {

    when(newsService.getNews(0, 1, "DESC")).thenReturn(ObjectMother.buildResponseBody());

    ResponseBody responseBody = newsService.getNews(0, 1, "DESC");

    Assertions.assertThat(responseBody.getNews()).hasSize(5);
  }
}
