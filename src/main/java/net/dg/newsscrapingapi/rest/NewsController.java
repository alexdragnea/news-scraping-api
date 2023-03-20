package net.dg.newsscrapingapi.rest;

import lombok.AllArgsConstructor;
import net.dg.newsscrapingapi.model.ResponseBody;
import net.dg.newsscrapingapi.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/news")
@CrossOrigin
public class NewsController {

  private final NewsService newsService;
  private static final Logger LOGGER = LoggerFactory.getLogger(ScrapeController.class);

  @GetMapping()
  public ResponseEntity<ResponseBody> getAllNews(@RequestParam String page) {
    LOGGER.info("Inside of getAllNews method of NewsController");

    return ResponseEntity.ok(newsService.getNews(Integer.parseInt(page), 30));
  }

  @GetMapping("/search")
  public ResponseEntity<ResponseBody> searchNews(@RequestParam String keyword) {

    LOGGER.info("Inside of searchNews method of NewsController");

    return ResponseEntity.ok(newsService.findByKeyword(keyword));
  }
}
