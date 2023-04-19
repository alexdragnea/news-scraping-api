package net.dg.newsscrapingapi.rest;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import net.dg.newsscrapingapi.helper.ObjectMother;
import net.dg.newsscrapingapi.model.ResponseBody;
import net.dg.newsscrapingapi.service.NewsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(NewsController.class)
class NewsControllerTest {

  @Autowired MockMvc mockMvc;

  @MockBean private NewsServiceImpl newsService;

  @Test
  void testGetAllNewsSortASC() throws Exception {

    when(newsService.getNews(anyInt(), anyInt(), anyString()))
        .thenReturn(ObjectMother.buildResponseBody());

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/v1/news?page=0&order=ASC")
                .header(
                    "api-key",
                    "ENC(lZBvBIaqpb/jzA8tpfzn1S08GaD7xRk9ku0IgNZG2KSKBAKFYymGoU1dxREcv8iW/7mCtKD6NZpiO87rJLZbHm2gRYAvg+ifsKrvrslXXpE=)")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.news").isNotEmpty())
        .andExpect(jsonPath("$.totalResults").value(5));
  }

  @Test
  void testGetAllNewsSortDESC() throws Exception {

    when(newsService.getNews(anyInt(), anyInt(), anyString()))
        .thenReturn(ObjectMother.buildResponseBody());

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/v1/news?page=0&order=DESC")
                .header(
                    "api-key",
                    "ENC(lZBvBIaqpb/jzA8tpfzn1S08GaD7xRk9ku0IgNZG2KSKBAKFYymGoU1dxREcv8iW/7mCtKD6NZpiO87rJLZbHm2gRYAvg+ifsKrvrslXXpE=)")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.news").isNotEmpty())
        .andExpect(jsonPath("$.totalResults").value(5));
  }

  @Test
  void testSearchNews() throws Exception {

    when(newsService.findByKeyword(anyString())).thenReturn(ObjectMother.buildResponseBody());

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/v1/news/search?keyword=test")
                .header(
                    "api-key",
                    "ENC(lZBvBIaqpb/jzA8tpfzn1S08GaD7xRk9ku0IgNZG2KSKBAKFYymGoU1dxREcv8iW/7mCtKD6NZpiO87rJLZbHm2gRYAvg+ifsKrvrslXXpE=)")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.news").isNotEmpty())
        .andExpect(jsonPath("$.totalResults").value(5));
  }

  @Test
  void testSearchNewsNothingFound() throws Exception {

    when(newsService.findByKeyword(anyString())).thenReturn(new ResponseBody());

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/v1/news/search?keyword=test")
                .header(
                    "api-key",
                    "ENC(lZBvBIaqpb/jzA8tpfzn1S08GaD7xRk9ku0IgNZG2KSKBAKFYymGoU1dxREcv8iW/7mCtKD6NZpiO87rJLZbHm2gRYAvg+ifsKrvrslXXpE=)")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.news").isEmpty())
        .andExpect(jsonPath("$.totalResults").doesNotExist());
  }
}
