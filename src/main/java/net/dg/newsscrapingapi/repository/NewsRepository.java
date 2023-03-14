package net.dg.newsscrapingapi.repository;

import java.util.List;
import java.util.Optional;
import net.dg.newsscrapingapi.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

  Optional<News> getNewsByTitle(String title);

  @Query(value = "SELECT * FROM news p WHERE p.title like %:keyword% ", nativeQuery = true)
  List<News> findByKeyword(@Param("keyword") String keyword);
}
