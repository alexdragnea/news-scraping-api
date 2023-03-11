package net.dg.newsscrapingapi.rest;

import java.util.Set;
import lombok.AllArgsConstructor;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.service.ScraperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class TestController {

  private final ScraperService scraperService;

  @GetMapping("/test")
  public Set<News> testEndpoint(){
    return scraperService.scrapeNews();
  }

}
