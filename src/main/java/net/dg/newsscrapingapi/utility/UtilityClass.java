package net.dg.newsscrapingapi.utility;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import net.dg.newsscrapingapi.constants.Source;
import net.dg.newsscrapingapi.model.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UtilityClass {

  public static void extractDataFromGizmodo(List<News> newsList, String url) {

    try {
      Document document = Jsoup.connect(url).get();

      Element element =
          document.getElementsByClass("sc-1kfqkp9-0 lboISa sc-1s9hjy6-0 llwBwG").first();

      Elements elements = element.getElementsByTag("article");
      for (Element ads : elements) {
        News news = new News();

        if (!isEmpty(ads.select("a").attr("title"))) {
          news.setTitle(ads.select("a").attr("title"));
          news.setUrl(ads.select("a").attr("href"));
          String text = ads.select("img").attr("data-srcset");
          String[] sentences = text.split(", ");
          news.setImgSrc(sentences[3]);
          news.setSource(Source.GHIZMODO.getSource());
          news.setScrapedDateTime(LocalDateTime.from(LocalDateTime.now()));
        }
        if (news.getUrl() != null) {
          newsList.add(news);
        }
      }

    } catch (IOException e) {
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
      for (Element ads : elements) {
        News news = new News();

        if (!isEmpty(ads.select("a").first().text())) {
          news.setTitle(ads.select("a").first().text());
          news.setUrl("https://mashable.com" + ads.select("a").attr("href"));
          news.setImgSrc(ads.select("img").attr("src"));
          news.setSource(Source.MASHABLE.getSource());
          news.setScrapedDateTime(LocalDateTime.from(LocalDateTime.now()));
        }
        if (news.getUrl() != null) {
          newsList.add(news);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
