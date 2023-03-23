package net.dg.newsscrapingapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import net.dg.newsscrapingapi.helper.ObjectMother;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.model.ResponseBody;
import net.dg.newsscrapingapi.repository.NewsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@RunWith(MockitoJUnitRunner.class)
public class NewsServiceImplTest {

  @Mock private NewsRepository newsRepository;
  @InjectMocks private NewsServiceImpl newsService;

  @Test
  public void testGetNewsDescendingOrder() {

    List<News> expectedNewsList = ObjectMother.buildListNews();
    when(newsRepository.findAll(any(PageRequest.class)))
        .thenReturn(new PageImpl<>(expectedNewsList));

    ResponseBody responseBody = newsService.getNews(1, 5, "DESC");
    verify(newsRepository)
        .findAll(PageRequest.of(1, 5, Sort.by(Sort.Direction.DESC, "scrapedDateTime")));
    assertEquals(expectedNewsList, responseBody.getNews());
    assertEquals(expectedNewsList.size(), responseBody.getNews().size());
  }

  @Test
  public void testGetNewsAscendingOrder() {
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
}
