package com.jfelixy.encurtadorurl.service;


import com.jfelixy.encurtadorurl.repository.UrlMappingRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UrlShortenerServiceTest {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @BeforeEach()
    void setup(){
        urlMappingRepository.deleteAll();
    }

    @Test
    void testGenerateShortUrl(){
        String longUrl = "https://www.google.com";
        String shortUrl = urlShortenerService.encurtadorUrl(longUrl);
        System.out.println(">>>>>>>>>>> http://localhost:8080/" + shortUrl);

    }



}
