package net.dg.newsscrapingapi.rest;

import net.dg.newsscrapingapi.helper.ObjectMother;
import net.dg.newsscrapingapi.service.NewsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(NewsController.class)
class ScrapeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private NewsServiceImpl newsService;

    @Test
    void testScrapeNews() throws Exception {

        when(newsService.scrapeNews())
                .thenReturn(ObjectMother.buildListNews());
    }


}