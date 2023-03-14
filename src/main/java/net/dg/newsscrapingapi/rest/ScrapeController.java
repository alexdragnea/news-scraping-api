package net.dg.newsscrapingapi.rest;

import java.util.List;
import lombok.AllArgsConstructor;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.service.ScraperService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/latestnews")
  public ResponseEntity<List<News>> latestNews(@RequestParam(required = false) String keyword) {

    LOGGER.info("Inside of latestNews method of ScrapeController");

    if (StringUtils.isEmpty(keyword)) {
      return ResponseEntity.ok(scraperService.findLatestNews(1, 20));
    }

    return ResponseEntity.ok(scraperService.findByKeyword(keyword));
  }
}
