package net.dg.newsscrapingapi.utility;


import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.io.IOException;
import java.util.Set;
import net.dg.newsscrapingapi.model.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UtilityClass {

  public static void extractDataFromGizmodo(Set<News> newsSet, String url) {

    try {
      Document document = Jsoup.connect(url).get();

      Element element = document.getElementsByClass("sc-1pxu3ok-1 sTtSe").first();

      Elements elements = element.getElementsByTag("article");
      for (Element ads : elements) {
        News news = new News();

        if (!isEmpty(ads.select("a").attr("title"))) {
          news.setTitle(ads.select("a").attr("title"));
          news.setUrl(ads.select("a").attr("href"));
          news.setImgSrc(ads.getElementsByTag("img").attr("data-src"));
          news.setSource("Gizmodo.com");
        }
        if (news.getUrl() != null) {
          newsSet.add(news);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void extractDataFromMashable(Set<News> newsSet, String url) {
    try {
      Document document = Jsoup.connect(url).get();

      Element element = document.getElementsByClass(
          "flex overflow-x-auto overflow-y-hidden flex-row space-x-8").first();

      Elements elements = element.getElementsByClass("flex-1");
      for (Element ads : elements) {
        News news = new News();

        if (!isEmpty(ads.select("a").last().text())) {
          news.setTitle(ads.select("a").last().text());
          news.setUrl("https://mashable.com" + ads.select("a").attr("href"));
          news.setImgSrc(ads.select("img").attr("src"));
          news.setSource("Mashable.com");
        }
        if (news.getUrl() != null) {
          newsSet.add(news);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
