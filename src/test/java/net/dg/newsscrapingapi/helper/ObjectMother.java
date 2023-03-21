package net.dg.newsscrapingapi.helper;

import java.time.LocalDateTime;
import java.util.List;
import net.dg.newsscrapingapi.model.News;
import net.dg.newsscrapingapi.model.ResponseBody;

public class ObjectMother {

  public static ResponseBody buildResponseBody() {
    return ResponseBody.builder()
        .news(buildListNews())
        .totalResults((long) buildListNews().size())
        .build();
  }

  public static List<News> buildListNews() {
    return List.of(
        new News(1L, "Spring Starts Here", "News", "News", "News", LocalDateTime.now()),
        new News(2L, "News", "News", "News", "News", LocalDateTime.now()),
        new News(3L, "News", "News", "News", "News", LocalDateTime.now()),
        new News(4L, "News", "News", "News", "News", LocalDateTime.now()),
        new News(5L, "News", "News", "News", "News", LocalDateTime.now()));
  }
}
