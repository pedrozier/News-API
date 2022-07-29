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

import com.standard.newsAPI.models.News;
import com.standard.newsAPI.repositories.NewsRepository;

@RestController
@RequestMapping("/News")
public class NewsController {

    @Autowired
    NewsRepository newsRepository;

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
    public ResponseEntity<?> saveNews(@RequestBody @Valid News news) {
        newsRepository.save(news);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNews(@RequestBody @Valid News news, @PathVariable @Valid long id) {
        Optional<News> optionalNews = newsRepository.findById(id);
        if (!optionalNews.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            news.setNews_id(optionalNews.get().getNews_id());
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
