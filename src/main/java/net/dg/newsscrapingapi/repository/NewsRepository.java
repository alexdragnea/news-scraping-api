package net.dg.newsscrapingapi.repository;

import net.dg.newsscrapingapi.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {}
