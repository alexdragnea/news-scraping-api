package net.dg.newsscrapingapi.service;

import java.util.List;
import net.dg.newsscrapingapi.model.News;

public interface ScraperService {

  List<News> scrapeNews();

  List<News> findLatestNews(int pageNmber, int size);
  List<News> findByKeyword(String keyword);
}
