package net.dg.newsscrapingapi.model;

import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseBody {

  private List<News> news;
  private Long totalResults;
}
