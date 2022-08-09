package com.standard.newsAPI.api.controllers;

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

import com.standard.newsAPI.api.models.NewsCategory;
import com.standard.newsAPI.api.repositories.NewsCategoryRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/category")
public class NewsCategoryController {

    @Autowired
    NewsCategoryRepository newsCategoryRepository;

    @GetMapping("/")
    public ResponseEntity<List<NewsCategory>> getAllNewsCategory() {
        List<NewsCategory> newsCategoryList = newsCategoryRepository.findAll();

        if (!newsCategoryList.isEmpty()) {
            for (NewsCategory newsCategory : newsCategoryList) {
                long id = newsCategory.getCategory_id();

                newsCategory.add(linkTo(methodOn(NewsCategoryController.class).getNewsCategoryById(id)).withSelfRel());
            }
            return new ResponseEntity<List<NewsCategory>>(newsCategoryList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsCategory> getNewsCategoryById(@PathVariable @Valid long id) {
        Optional<NewsCategory> newsCategory = newsCategoryRepository.findById(id);
        if (!newsCategory.isPresent()) {
            return new ResponseEntity<NewsCategory>(HttpStatus.NOT_FOUND);
        } else {
            NewsCategory categoryModel = newsCategory.get();
            categoryModel.add(linkTo(methodOn(NewsCategoryController.class).getAllNewsCategory())
                    .withRel("Categories List"));
            return new ResponseEntity<>(categoryModel, HttpStatus.OK);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveNewsCategory(@RequestBody @Valid NewsCategory newsCategory) {
        newsCategoryRepository.save(newsCategory);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNewsCategory(@RequestBody @Valid NewsCategory newsCategory,
            @PathVariable @Valid long id) {
        Optional<NewsCategory> optionalNewsCategory = newsCategoryRepository.findById(id);
        if (!optionalNewsCategory.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            newsCategory.setCategory_id(optionalNewsCategory.get().getCategory_id());
            newsCategoryRepository.save(newsCategory);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNewsCategory(@PathVariable @Valid long id) {
        newsCategoryRepository.delete(newsCategoryRepository.findById(id).get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
