package net.dg.newsscrapingapi.utility;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.dg.newsscrapingapi.model.News;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UtilityClassTest {

  private final String GIZMODO_URL = "https://gizmodo.com/tech/news";
  private final String MASHABLE_URL = "https://mashable.com/tech";
  private final String MEDIAFAX_URL = "https://www.mediafax.ro/tehnologie/";

  @Test
  void testExtractData() throws IOException {

    ConcurrentLinkedQueue<News> gizmodoNewsList = new ConcurrentLinkedQueue<>();
    ConcurrentLinkedQueue<News> mashableNewsList = new ConcurrentLinkedQueue<>();
    ConcurrentLinkedQueue<News> mediafaxNewsList = new ConcurrentLinkedQueue<>();
    UtilityClass.extractDataFromGizmodo(gizmodoNewsList, GIZMODO_URL);
    UtilityClass.extractDataFromMashable(mashableNewsList, MASHABLE_URL);
    UtilityClass.extractDataFromMediafax(mediafaxNewsList, MEDIAFAX_URL);
    Assertions.assertFalse(gizmodoNewsList.isEmpty());
    Assertions.assertFalse(mashableNewsList.isEmpty());
    Assertions.assertFalse(gizmodoNewsList.isEmpty());
  }
}
