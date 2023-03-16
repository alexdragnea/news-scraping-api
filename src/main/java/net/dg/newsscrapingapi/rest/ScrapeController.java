package net.dg.newsscrapingapi.rest;

import java.util.List;
import lombok.AllArgsConstructor;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.model.ResponseBody;
import net.dg.newsscrapingapi.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ScrapeController {

  private final NewsService newsService;
  private static final Logger LOGGER = LoggerFactory.getLogger(ScrapeController.class);

  @GetMapping("/scrapenews")
  public ResponseEntity<List<News>> scrapeNews() {

    LOGGER.info("Inside of scrapeNews method of ScrapeController");
    return ResponseEntity.ok(newsService.scrapeNews());
  }

  @GetMapping("/news")
  public ResponseEntity<ResponseBody> getAllNews(@RequestParam String page) {
    LOGGER.info("Inside of latestNews method of ScrapeController");

    return ResponseEntity.ok(newsService.getNews(Integer.parseInt(page), 30));
  }

  @GetMapping("/news/search")
  public ResponseEntity<List<News>> searchNews(@RequestParam String keyword) {

    LOGGER.info("Inside of searchNews method of ScrapeController");

    return ResponseEntity.ok(newsService.findByKeyword(keyword));
  }
}
