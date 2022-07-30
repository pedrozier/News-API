package com.standard.newsAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.standard.newsAPI.models.News;

public interface NewsRepository extends JpaRepository<News, Long> {

}
