package com.example.application.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LandingPageController {

    private final ResourceLoader resourceLoader;

    public LandingPageController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/landing")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Välkommen till landningssidan!");
    }
}

