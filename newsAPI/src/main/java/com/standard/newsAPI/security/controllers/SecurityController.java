package com.standard.newsAPI.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.standard.newsAPI.security.repositories.UserRepository;

@RestController
public class SecurityController {

    @Autowired
    UserRepository userRepository;

}
