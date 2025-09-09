package com.jfelixy.encurtadorurl.controller;


import com.jfelixy.encurtadorurl.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/encurtador")
public class UrlShortenerController {

    @Autowired
    UrlShortenerService urlShortenerService;

    @PostMapping
    public ResponseEntity<String> encurtarUrl(@RequestBody String url){
        String urlEncurtada = urlShortenerService.encurtadorUrl(url);
        return ResponseEntity.created(URI.create(urlEncurtada)).body(urlEncurtada);
    }



}
