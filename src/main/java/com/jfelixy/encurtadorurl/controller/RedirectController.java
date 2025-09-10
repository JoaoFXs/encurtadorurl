package com.jfelixy.encurtadorurl.controller;


import com.jfelixy.encurtadorurl.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class RedirectController {

    @Autowired
    UrlShortenerService urlShortenerService;

    /**
     * Controlador para redirecionar a urlencurtada para urlLonga
     * @param shortUrl
     * @param response
     * @throws IOException
     */
    @GetMapping("/{shortUrl}")
    public void redirecionarParaUrlLonga(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        /** Captura a atual request e remove o seu path**/
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        builder.replacePath("");
        String urlLonga = urlShortenerService.obterUrlLonga(builder.build() + "/" + shortUrl);
        System.out.println(urlLonga);
        response.sendRedirect(urlLonga);
    }





}
