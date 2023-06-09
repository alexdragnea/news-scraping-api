package net.dg.newsscrapingapi.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.AllArgsConstructor;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.model.ResponseBody;
import net.dg.newsscrapingapi.repository.NewsRepository;
import net.dg.newsscrapingapi.utility.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {

  private final NewsRepository newsRepository;

  @Value("#{'${website.urls}'.split(',')}")
  private List<String> urls;

  @Override
  public List<News> scrapeNews() {
    ConcurrentLinkedQueue<News> newsList = new ConcurrentLinkedQueue<>();
    Set<News> uniqueNews = new HashSet<>();

    for (String url : urls) {

      if (url.contains("mashable")) {

        UtilityClass.extractDataFromMashable(newsList, url);
      } else if (url.contains("gizmodo")) {
        UtilityClass.extractDataFromGizmodo(newsList, url);
      } else if (url.contains("mediafax")) {
        UtilityClass.extractDataFromMediafax(newsList, url);
      } else if (url.contains("galaxy-tech")) {
        UtilityClass.extractDataFromGalaxyTech(newsList, url);
      }
    }

    for (News news : newsList) {
      Optional<News> existingNews = newsRepository.getNewsByTitle(news.getTitle());
      if (existingNews.isEmpty()) uniqueNews.add(news);
    }

    return newsRepository.saveAll(uniqueNews);
  }

  @Override
  public ResponseBody findByKeyword(String keyword) {

    List<News> newsList = newsRepository.findByKeyword(keyword);

    return new ResponseBody(newsList, (long) newsList.size());
  }

  @Override
  public ResponseBody getNews(int pageNmber, int size, String order) {

    if (order.equalsIgnoreCase("DESC")) {
      return new ResponseBody(
          newsRepository
              .findAll(
                  PageRequest.of(pageNmber, size, Sort.by(Sort.Direction.DESC, "scrapedDateTime")))
              .getContent(),
          newsRepository.count());
    }

    return new ResponseBody(
        newsRepository
            .findAll(
                PageRequest.of(pageNmber, size, Sort.by(Sort.Direction.ASC, "scrapedDateTime")))
            .getContent(),
        newsRepository.count());
  }
}
