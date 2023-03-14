package net.dg.newsscrapingapi.rest;

import java.util.List;
import lombok.AllArgsConstructor;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.service.ScraperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ScrapeController {

  private final ScraperService scraperService;
  private static final Logger LOGGER = LoggerFactory.getLogger(ScrapeController.class);

  @GetMapping("/scrapeNews")
  public ResponseEntity<List<News>> scrapeNews() {

    LOGGER.info("Inside of scrapeNews method of ScrapeController");
    return ResponseEntity.ok(scraperService.scrapeNews());
  }

  @GetMapping("/latestNews")
  public ResponseEntity<List<News>> latestNews() {

    LOGGER.info("Inside of latestNews method of ScrapeController");
    return ResponseEntity.ok(scraperService.findLatestNews(1, 20));
  }

  @GetMapping("/news/search")
  public ResponseEntity<List<News>> findNewsByKeyword(@RequestParam String keyword) {

    LOGGER.info("Inside of findNewsByKeyword method of ScrapeController");
    return ResponseEntity.ok(scraperService.findByKeyword(keyword));
  }
}
