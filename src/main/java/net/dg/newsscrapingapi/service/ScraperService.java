package net.dg.newsscrapingapi.service;

import java.util.List;
import net.dg.newsscrapingapi.model.News;
import org.springframework.data.domain.Page;

public interface ScraperService {

  List<News> scrapeNews();
  List<News> findLatest20News();
}
