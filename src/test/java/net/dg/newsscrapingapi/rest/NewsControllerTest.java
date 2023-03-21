package net.dg.newsscrapingapi.rest;

import net.dg.newsscrapingapi.helper.ResponseBodyObjectMother;
import net.dg.newsscrapingapi.service.NewsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NewsController.class)
class NewsControllerTest {

  @Autowired MockMvc mockMvc;

  @MockBean private NewsServiceImpl newsService;

  @Test
  void testGetAllNews() throws Exception {

    when(newsService.getNews(anyInt(), anyInt()))
        .thenReturn(ResponseBodyObjectMother.buildResponseBody());

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/v1/news?page=0")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.news").isNotEmpty())
        .andExpect(jsonPath("$.totalResults").value(5));
  }
}
