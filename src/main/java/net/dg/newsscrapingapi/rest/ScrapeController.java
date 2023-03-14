package net.dg.newsscrapingapi.rest;

import java.util.List;
import lombok.AllArgsConstructor;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.service.ScraperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ScrapeController {

  private final ScraperService scraperService;
  private static final Logger LOGGER = LoggerFactory.getLogger(ScrapeController.class);

  @GetMapping("/scrapeNews")
  public List<News> scrapeNews() {

    LOGGER.info("Inside of latestNews method of ScrapeController");
    return scraperService.scrapeNews();
  }

  @GetMapping("/latestNews")
  public List<News> latestNews() {

    LOGGER.info("Inside of latest10News method of ScrapeController");
    return scraperService.findLatestNews();
  }
}
