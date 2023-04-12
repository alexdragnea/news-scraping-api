package net.dg.newsscrapingapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import net.dg.newsscrapingapi.helper.ObjectMother;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.model.ResponseBody;
import net.dg.newsscrapingapi.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

  @Mock private NewsRepository newsRepository;
  @InjectMocks private NewsServiceImpl newsService;

  @Test
  void testGetNewsDescendingOrder() {

    List<News> expectedNewsList = ObjectMother.buildListNews();
    when(newsRepository.findAll(any(PageRequest.class)))
        .thenReturn(new PageImpl<>(expectedNewsList));

    ResponseBody responseBody = newsService.getNews(1, 5, "DESC");
    verify(newsRepository)
        .findAll(PageRequest.of(1, 5, Sort.by(Sort.Direction.DESC, "scrapedDateTime")));
    assertEquals(expectedNewsList, responseBody.getNews());
    assertEquals(expectedNewsList.size(), responseBody.getNews().size());

    assertThrows(IllegalArgumentException.class, () -> newsService.getNews(-1, 5, "DESC"));
  }

  @Test
  void testGetNewsAscendingOrder() {
    List<News> expectedNewsList = ObjectMother.buildListNews();
    when(newsRepository.findAll(any(PageRequest.class)))
        .thenReturn(new PageImpl<>(expectedNewsList));

    ResponseBody responseBody = newsService.getNews(1, 5, "ASC");

    // Then
    verify(newsRepository)
        .findAll(PageRequest.of(1, 5, Sort.by(Sort.Direction.ASC, "scrapedDateTime")));
    assertEquals(expectedNewsList, responseBody.getNews());
    assertEquals(expectedNewsList.size(), responseBody.getNews().size());
  }

  @Test
  void testFindByKeyword() {
    List<News> expectedNewsList = ObjectMother.buildListNews();
    when(newsRepository.findByKeyword(any(String.class))).thenReturn(expectedNewsList);

    ResponseBody responseBody = newsService.findByKeyword("test");

    verify(newsRepository).findByKeyword("test");
    assertEquals(expectedNewsList, responseBody.getNews());
    assertEquals(expectedNewsList.size(), responseBody.getNews().size());
  }
}
