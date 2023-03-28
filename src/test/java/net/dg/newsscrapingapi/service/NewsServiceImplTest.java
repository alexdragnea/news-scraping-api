package net.dg.newsscrapingapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import net.dg.newsscrapingapi.helper.ObjectMother;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.model.ResponseBody;
import net.dg.newsscrapingapi.repository.NewsRepository;
import net.dg.newsscrapingapi.utility.UtilityClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
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

  @Test
  public void testFindByKeyword() {
    List<News> expectedNewsList = ObjectMother.buildListNews();
    when(newsRepository.findByKeyword(any(String.class))).thenReturn(expectedNewsList);

    ResponseBody responseBody = newsService.findByKeyword("test");

    verify(newsRepository).findByKeyword("test");
    assertEquals(expectedNewsList, responseBody.getNews());
    assertEquals(expectedNewsList.size(), responseBody.getNews().size());
  }

  @Test
  public void testScrapeNews() {
    // Create a mock news repository
    NewsRepository mockRepository = Mockito.mock(NewsRepository.class);

    // Create a news service with the mock repository
    NewsServiceImpl newsService =
        new NewsServiceImpl(
            mockRepository,
            List.of("https://mashable.com", "https://gizmodo.com", "https://mediafax.com"));

    try (MockedStatic<UtilityClass> mockedStatic = Mockito.mockStatic(UtilityClass.class)) {
      Mockito.doNothing().when(UtilityClass.class);
      UtilityClass.extractDataFromMashable(any(), anyString());
      Mockito.doNothing().when(UtilityClass.class);
      UtilityClass.extractDataFromGizmodo(any(), anyString());
      Mockito.doNothing().when(UtilityClass.class);
      UtilityClass.extractDataFromMediafax(any(), anyString());

      // Call the scrapeNews method
      newsService.scrapeNews();

      // Verify that the static methods were called
      mockedStatic.verify(() -> UtilityClass.extractDataFromMashable(any(), anyString()));
      mockedStatic.verify(() -> UtilityClass.extractDataFromGizmodo(any(), anyString()));
      mockedStatic.verify(() -> UtilityClass.extractDataFromMediafax(any(), anyString()));

      // Verify that the news were saved to the repository
      Mockito.verify(mockRepository, Mockito.times(1)).saveAll(anyList());
    }
  }
}
