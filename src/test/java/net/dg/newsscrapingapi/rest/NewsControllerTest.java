package net.dg.newsscrapingapi.rest;

import net.dg.newsscrapingapi.helper.ResponseBodyObjectMother;
import net.dg.newsscrapingapi.service.NewsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(NewsController.class)
class NewsControllerTest {

  @Autowired MockMvc mockMvc;

  @MockBean private NewsServiceImpl newsService;

  @Test
  void testGetAllNews() throws Exception {

    when(newsService.getNews(any(), any()))
        .thenReturn(ResponseBodyObjectMother.buildResponseBody());
  }
}
