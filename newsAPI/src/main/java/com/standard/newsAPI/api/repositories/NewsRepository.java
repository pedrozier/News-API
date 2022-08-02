package com.standard.newsAPI.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.standard.newsAPI.api.models.News;

public interface NewsRepository extends JpaRepository<News, Long> {

}
