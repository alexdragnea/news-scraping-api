package net.dg.newsscrapingapi.service;

import java.util.Set;
import net.dg.newsscrapingapi.model.News;

public interface ScraperService {

  Set<News> scrapeNews();


}
