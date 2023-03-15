package net.dg.newsscrapingapi.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBody {

  private List<News> news;
  private Long totalResults;
}
