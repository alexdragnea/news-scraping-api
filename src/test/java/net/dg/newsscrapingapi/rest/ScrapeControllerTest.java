package net.dg.newsscrapingapi.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import net.dg.newsscrapingapi.helper.ObjectMother;
import net.dg.newsscrapingapi.repository.NewsRepository;
import net.dg.newsscrapingapi.service.NewsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(ScrapeController.class)
class ScrapeControllerTest {

  @Autowired MockMvc mockMvc;

  @Mock private NewsRepository newsRepository;

  @MockBean private NewsServiceImpl newsService;

  @Test
  void testScrapeNews() throws Exception {

    when(newsService.scrapeNews()).thenReturn(ObjectMother.buildListNews());

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/v1/scrapenews")
                .header(
                    "api-key",
                    "ENC(lZBvBIaqpb/jzA8tpfzn1S08GaD7xRk9ku0IgNZG2KSKBAKFYymGoU1dxREcv8iW/7mCtKD6NZpiO87rJLZbHm2gRYAvg+ifsKrvrslXXpE=)")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(5)));
  }
}
