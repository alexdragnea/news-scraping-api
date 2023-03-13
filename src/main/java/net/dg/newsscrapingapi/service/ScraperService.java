package net.dg.newsscrapingapi.service;

import java.util.List;
import java.util.Set;
import net.dg.newsscrapingapi.model.News;

public interface ScraperService {

  List<News> scrapeNews();
}
