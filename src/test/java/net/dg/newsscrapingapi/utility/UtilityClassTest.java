package net.dg.newsscrapingapi.utility;

import net.dg.newsscrapingapi.model.News;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

class UtilityClassTest {

  private final String GIZMODO_URL = "https://gizmodo.com/tech/news";
  private final String MASHABLE_URL = "https://mashable.com/tech";

  @Test
  void testExtractData() throws IOException {

    ConcurrentLinkedQueue<News> gizmodoNewsList = new ConcurrentLinkedQueue<>();
    ConcurrentLinkedQueue<News> mashableNewsList = new ConcurrentLinkedQueue<>();
    UtilityClass.extractDataFromGizmodo(gizmodoNewsList, GIZMODO_URL);
    UtilityClass.extractDataFromMashable(mashableNewsList, MASHABLE_URL);
      Assertions.assertFalse(gizmodoNewsList.isEmpty());
      Assertions.assertFalse(mashableNewsList.isEmpty());
  }

}
