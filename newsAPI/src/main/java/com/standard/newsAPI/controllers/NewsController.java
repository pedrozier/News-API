package com.standard.newsAPI.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.standard.newsAPI.dto.NewsDTO;
import com.standard.newsAPI.models.News;
import com.standard.newsAPI.models.NewsCategory;
import com.standard.newsAPI.repositories.NewsCategoryRepository;
import com.standard.newsAPI.repositories.NewsRepository;

@RestController
@RequestMapping("/News")
public class NewsController {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    NewsCategoryRepository newsCategoryRepository;

    @GetMapping("/")
    public ResponseEntity<List<News>> getAllNews() {
        return new ResponseEntity<List<News>>(newsRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable @Valid long id) {
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            return new ResponseEntity<News>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(news.get(), HttpStatus.OK);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveNews(@RequestBody @Valid NewsDTO newsDTO) {
        Optional<NewsCategory> category = newsCategoryRepository.findById(newsDTO.getCategory());
        if (!category.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            News news = new News();
            news.setTitle(newsDTO.getTitle());
            news.setContent(newsDTO.getContent());
            news.setCategory(category.get());
            newsRepository.save(news);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNews(@RequestBody @Valid NewsDTO newsDTO, @PathVariable @Valid long id) {
        Optional<NewsCategory> optionalCategory = newsCategoryRepository.findById(newsDTO.getCategory());
        Optional<News> optionalNews = newsRepository.findById(id);
        if (!optionalNews.isPresent() && !optionalCategory.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            News news = optionalNews.get();
            news.setTitle(newsDTO.getTitle());
            news.setContent(newsDTO.getContent());
            news.setCategory(optionalCategory.get());
            newsRepository.save(news);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable @Valid long id) {
        newsRepository.delete(newsRepository.findById(id).get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
