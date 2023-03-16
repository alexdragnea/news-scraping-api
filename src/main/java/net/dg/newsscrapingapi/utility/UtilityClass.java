package net.dg.newsscrapingapi.utility;

import net.dg.newsscrapingapi.constants.Source;
import net.dg.newsscrapingapi.model.News;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class UtilityClass {

  public static void extractDataFromGizmodo(List<News> newsList, String url) {
    try {
      Document document = Jsoup.connect(url).get();
      Element element = document.getElementsByClass("sc-17uq8ex-0 fakHlO").first();
      Elements elements = element.getElementsByTag("article");

      ExecutorService executorService = Executors.newFixedThreadPool(elements.size());

      for (Element ads : elements) {
        executorService.submit(
            () -> {
              News news = new News();

              if (!isEmpty(ads.select("img").attr("alt"))) {
                news.setTitle(ads.select("img").attr("alt"));
                news.setUrl(extractUrlFromGizmodo(ads.select("a").last().attr("data-ga")));
                news.setImgSrc(ads.select("source").attr("data-srcset"));
                news.setSource(Source.GHIZMODO.getSource());
                news.setScrapedDateTime(LocalDateTime.now());
              }
              if (news.getUrl() != null) {
                synchronized (newsList) {
                  newsList.add(news);
                }
              }
            });
      }

      executorService.shutdown();
      executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void extractDataFromMashable(List<News> newsList, String url) {
    try {
      Document document = Jsoup.connect(url).get();
      Element element = document.getElementsByClass("justify-center mt-8 w-full").first();
      Elements elements =
          element.getElementsByClass(
              "flex flex-row mx-auto mt-4 max-w-4xl font-sans md:flex-nowrap md:justify-around md:mx-0 md:mt-8");

      ExecutorService executorService = Executors.newFixedThreadPool(elements.size());

      for (Element ads : elements) {
        executorService.submit(
            () -> {
              News news = new News();

              if (!isEmpty(ads.select("a").first().text())) {
                news.setTitle(ads.select("a").first().text());
                news.setUrl("https://mashable.com" + ads.select("a").attr("href"));
                news.setImgSrc(ads.select("img").attr("src"));
                news.setSource(Source.MASHABLE.getSource());
                news.setScrapedDateTime(LocalDateTime.now());
              }
              if (news.getUrl() != null) {
                synchronized (newsList) {
                  newsList.add(news);
                }
              }
            });
      }

      executorService.shutdown();
      executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static String extractUrlFromGizmodo(String input) {
    String pattern = "(https?://[^\"]+)";
    Pattern regex = Pattern.compile(pattern);
    Matcher matcher = regex.matcher(input);
    if (matcher.find()) {
      return matcher.group(1);
    }
    return StringUtils.EMPTY;
  }
}
