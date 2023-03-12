package net.dg.newsscrapingapi.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.utility.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ScraperServiceImpl implements ScraperService {

  @Value("#{'${website.urls}'.split(',')}")
  private List<String> urls;

  @Override
  public Set<News> scrapeNews() {
    Set<News> newsSet = new HashSet<>();

    for (String url : urls) {

      if (url.contains("mashable")) {

        UtilityClass.extractDataFromMashable(newsSet, url);
      } else if (url.contains("gizmodo")) {
        UtilityClass.extractDataFromGizmodo(newsSet, url);
      }
    }

    return newsSet;
  }
}
