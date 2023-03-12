package net.dg.newsscrapingapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class News {

  private String title;
  private String url;
  private String imgSrc;
  private String source;

  @JsonInclude(Include.NON_NULL)
  private LocalDate publishedDate;

  private LocalDate scrapedDate;
}
