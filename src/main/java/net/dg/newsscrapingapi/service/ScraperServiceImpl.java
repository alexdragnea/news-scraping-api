package net.dg.newsscrapingapi.service;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.repository.NewsRepository;
import net.dg.newsscrapingapi.utility.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ScraperServiceImpl implements ScraperService {

  private final NewsRepository newsRepository;

  @Value("#{'${website.urls}'.split(',')}")
  private List<String> urls;

  @Override
  public List<News> scrapeNews() {
    List<News> newsSet = new ArrayList<>();

    for (String url : urls) {

      if (url.contains("mashable")) {

        UtilityClass.extractDataFromMashable(newsSet, url);
      } else if (url.contains("gizmodo")) {
        UtilityClass.extractDataFromGizmodo(newsSet, url);
      }
    }

    return newsRepository.saveAll(newsSet);
  }
}
