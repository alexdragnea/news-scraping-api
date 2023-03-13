package net.dg.newsscrapingapi.rest;

import lombok.AllArgsConstructor;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.service.ScraperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class TestController {

  private final ScraperService scraperService;

  @GetMapping("/test")
  public List<News> testEndpoint() {
    return scraperService.scrapeNews();
  }
}
