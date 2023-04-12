package net.dg.newsscrapingapi.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.repository.NewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleCleanUpService {

  private final NewsRepository newsRepository;
  private static final Logger LOGGER = LoggerFactory.getLogger(ArticleCleanUpService.class);

  @Scheduled(cron = "*/15 * * * * *") // Run once every 15 seconds
  //  // @Scheduled(cron = "0 * * * * *") // Run once every 1 minute
  //  @Scheduled(cron = "0 */5 * * * *") // Run once every 5 minutes
  public void removeNonExistentArticles() {
    List<News> allNews = newsRepository.findAll();
    HttpClient httpClient = HttpClient.newHttpClient();

    LOGGER.info("Inside ArticleCleanUpService");

    for (News news : allNews) {
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create(news.getUrl()))
              .method("HEAD", HttpRequest.BodyPublishers.noBody())
              .build();

      try {
        HttpResponse<Void> response =
            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        if (response.statusCode() == 404) {
          newsRepository.delete(news);
          LOGGER.info("ArticleCleanUpService: error non-existent article: {}", news.getTitle());
        }
      } catch (IOException | InterruptedException e) {
        LOGGER.error("ArticleCleanUpService: error checking article: {}", news.getTitle(), e);
        Thread.currentThread().interrupt();
      }
    }
  }
}
