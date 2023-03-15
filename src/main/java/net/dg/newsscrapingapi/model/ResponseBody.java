package net.dg.newsscrapingapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBody {

  private List<News> news;
  private BigInteger totalCount;
}
