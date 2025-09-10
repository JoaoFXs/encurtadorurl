package com.jfelixy.encurtadorurl.controller;


import com.jfelixy.encurtadorurl.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;


@RestController
@RequestMapping("/encurtador")
public class EncurtadorController {

    @Autowired
    UrlShortenerService urlShortenerService;

    /**
     * Controlador para encurtarUrl
     * @param url
     * @return
     */
    @PostMapping
    public ResponseEntity<String> encurtarUrl(@RequestBody String url){
        String urlEncurtada = urlShortenerService.encurtadorUrl(url);
        return ResponseEntity.created(URI.create(urlEncurtada)).body(urlEncurtada);
    }


}
