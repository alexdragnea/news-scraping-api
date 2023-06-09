package net.dg.newsscrapingapi.service;

import java.util.List;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.model.ResponseBody;

public interface NewsService {

  List<News> scrapeNews();

  ResponseBody findByKeyword(String keyword);

  ResponseBody getNews(int pageNmber, int size, String order);
}
