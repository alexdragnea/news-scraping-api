package net.dg.newsscrapingapi.service;

import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import net.dg.newsscrapingapi.model.News;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ScraperServiceImpl implements ScraperService {


  @Value("#{'${website.urls}'.split(',')}")
  private List<String> urls;

  @Override
  public Set<News> scrapeNews() {
    return null;
  }
}
