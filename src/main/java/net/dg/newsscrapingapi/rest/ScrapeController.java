package net.dg.newsscrapingapi.rest;

import lombok.AllArgsConstructor;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.model.ResponseBody;
import net.dg.newsscrapingapi.service.ScraperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ScrapeController {

  private final ScraperService scraperService;
  private static final Logger LOGGER = LoggerFactory.getLogger(ScrapeController.class);

  @GetMapping("/scrapenews")
  public ResponseEntity<List<News>> scrapeNews() {

    LOGGER.info("Inside of scrapeNews method of ScrapeController");
    return ResponseEntity.ok(scraperService.scrapeNews());
  }

  @GetMapping("/news")
  public ResponseEntity<ResponseBody> getAllNews(@RequestParam String page) {
    LOGGER.info("Inside of latestNews method of ScrapeController");

    return ResponseEntity.ok(scraperService.getNews(Integer.parseInt(page), 30));
  }

  @GetMapping("/news/search")
  public ResponseEntity<List<News>> searchNews(@RequestParam String keyword) {

    LOGGER.info("Inside of searchNews method of ScrapeController");

    return ResponseEntity.ok(scraperService.findByKeyword(keyword));
  }
}
