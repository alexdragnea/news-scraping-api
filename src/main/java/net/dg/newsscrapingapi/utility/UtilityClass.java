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
import java.time.format.DateTimeFormatter;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class UtilityClass {

  private UtilityClass() {}

  private static final DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
  private static final int THREAD_POOL_SIZE = 5;

  public static void extractDataFromGizmodo(Queue<News> newsList, String url) {
    try {
      Document document = Jsoup.connect(url).get();
      Element element = document.getElementsByClass("sc-17uq8ex-0 fakHlO").first();
      Elements elements = element.getElementsByTag("article");

      ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

      for (Element ads : elements) {
        executorService.submit(
            () -> {
              News news = new News();

              if (!isEmpty(ads.select("img").attr("alt"))) {
                news.setTitle(ads.select("img").attr("alt"));
                news.setUrl(extractUrl(ads.select("a").last().attr("data-ga")));
                news.setImgSrc(ads.select("source").attr("data-srcset"));
                news.setSource(Source.GHIZMODO.getSourceName());
                news.setScrapedDateTime(extractLocalDateTime());
              }
              if (news.getUrl() != null) {
                newsList.offer(news);
              }
            });
      }

      executorService.shutdown();
      executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }

  public static void extractDataFromMediafax(Queue<News> newsList, String url) {
    try {
      Document document = Jsoup.connect(url).get();
      Element element = document.getElementsByClass("intros").first();
      Elements elements = element.getElementsByTag("li");

      ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

      for (Element ads : elements) {
        executorService.submit(
            () -> {
              News news = new News();

              if (!isEmpty(ads.select("img").attr("alt"))) {
                news.setTitle((ads.select("img").attr("alt")).substring(21));
                news.setUrl(extractUrl(ads.select("a").last().attr("href")));
                news.setImgSrc(ads.select("img").attr("data-src"));
                news.setSource(Source.MEDIAFAX.getSourceName());
                news.setScrapedDateTime(extractLocalDateTime());
              }
              if (news.getUrl() != null) {
                newsList.offer(news);
              }
            });
      }

      executorService.shutdown();
      executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }

  public static void extractDataFromMashable(Queue<News> newsList, String url) {
    try {
      Document document = Jsoup.connect(url).get();
      Element element = document.getElementsByClass("justify-center mt-8 w-full").first();
      Elements elements =
          element.getElementsByClass(
              "flex flex-row mx-auto mt-4 max-w-4xl font-sans md:flex-nowrap md:justify-around md:mx-0 md:mt-8");

      ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

      for (Element ads : elements) {
        executorService.submit(
            () -> {
              News news = new News();

              if (!isEmpty(ads.select("a").first().text())) {
                news.setTitle(ads.select("a").first().text());
                news.setUrl("https://mashable.com" + ads.select("a").attr("href"));
                news.setImgSrc(ads.select("img").attr("src"));
                news.setSource(Source.MASHABLE.getSourceName());
                news.setScrapedDateTime(extractLocalDateTime());
              }
              if (news.getUrl() != null) {
                newsList.offer(news);
              }
            });
      }

      executorService.shutdown();
      executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }

  public static void extractDataFromGalaxyTech(Queue<News> newsList, String url) {
    try {
      Document document = Jsoup.connect(url).get();
      Element element = document.getElementsByClass("content-area").first();
      Elements elements = element.getElementsByTag("article");

      ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

      for (Element ads : elements) {
        executorService.submit(
            () -> {
              News news = new News();

              if (!isEmpty(ads.select("a").first().text())) {
                news.setTitle(ads.select("a").first().text());
                news.setUrl(extractUrl(ads.select("a").last().attr("href")));
                String imgSrc = ads.select("img").attr("srcset");
                news.setImgSrc(imgSrc.substring(0, imgSrc.indexOf(" ")));
                news.setSource(Source.GALAXYTECH.getSourceName());
                news.setScrapedDateTime(extractLocalDateTime());
              }
              if (news.getUrl() != null) {
                newsList.offer(news);
              }
            });
      }

      executorService.shutdown();
      executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      Thread.currentThread().interrupt();
    }
  }

  public static String extractUrl(String input) {
    String pattern = "(https?://[^\"]+)";
    Pattern regex = Pattern.compile(pattern);
    Matcher matcher = regex.matcher(input);
    if (matcher.find()) {
      return matcher.group(1);
    }
    return StringUtils.EMPTY;
  }

  public static LocalDateTime extractLocalDateTime() {
    LocalDateTime now = LocalDateTime.now();
    String formattedDate = now.format(formatter);
    return LocalDateTime.parse(formattedDate, formatter);
  }
}
