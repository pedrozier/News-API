package com.standard.newsAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.standard.newsAPI.models.NewsCategory;

public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Long> {

}
