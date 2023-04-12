package net.dg.newsscrapingapi.rest;

import java.util.List;
import lombok.AllArgsConstructor;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ScrapeController {

  private final NewsService newsService;
  private static final Logger LOGGER = LoggerFactory.getLogger(ScrapeController.class);

  @GetMapping("/scrapenews")
  public ResponseEntity<List<News>> scrapeNews() {

    List<News> newsList = newsService.scrapeNews();
    LOGGER.info("ScrapeController:, found {} number of news ", newsList.size());
    return ResponseEntity.ok(newsList);
  }
}
