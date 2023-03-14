package net.dg.newsscrapingapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class News {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String url;

  @Column(name = "img_source")
  private String imgSrc;

  private String source;

  @Column(name = "scraped_date")
  private LocalDateTime scrapedDate;
}
