package com.standard.newsAPI.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.standard.newsAPI.api.models.NewsCategory;

public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Long> {

}
