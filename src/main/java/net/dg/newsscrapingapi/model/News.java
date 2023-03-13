package net.dg.newsscrapingapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.*;
import java.time.LocalDate;
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

  @JsonInclude(Include.NON_NULL)
  @Transient
  @Column(name = "published_date")
  private LocalDate publishedDate;

  @Column(name = "scraped_date")
  private LocalDate scrapedDate;
}
