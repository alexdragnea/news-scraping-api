package net.dg.newsscrapingapi.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
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

  @Column(name = "scraped_date_time")
  private LocalDateTime scrapedDateTime;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    News news = (News) o;
    return id != null && Objects.equals(id, news.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
