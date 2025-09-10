package com.jfelixy.encurtadorurl.service;


import com.jfelixy.encurtadorurl.exceptions.FalhaaoPersistirException;
import com.jfelixy.encurtadorurl.exceptions.UrlNotFoundException;
import com.jfelixy.encurtadorurl.model.UrlMapping;
import com.jfelixy.encurtadorurl.repository.UrlMappingRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.stubbing.OngoingStubbing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UrlShortenerServiceTest {

    @Autowired
    private UrlShortenerService urlShortenerService;

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerServiceTest.class);

    @Autowired
    private UrlMappingRepository urlMappingRepository;


    @BeforeAll
    static void setupAll(){
        logger.info("================================================");
        logger.info("INICIANDO TESTES DO SERVIÇO URLSHORTENERSERVICE");
        logger.info("================================================");
    }

    @BeforeEach()
    void setup(TestInfo testInfo){
        logger.info("-------> Iniciando teste: {} <-------", testInfo.getDisplayName());
        urlMappingRepository.deleteAll();
        logger.debug("Banco de dados H2 limpo antes do teste.");
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        logger.info("-------> Finalizando teste: {} <-------\n", testInfo.getDisplayName());
    }
    @AfterAll
    static void tearDownAll() {
        logger.info("=================================================");
        logger.info("FINALIZANDO TESTES DO SERVIÇO URLSHORTENERSERVICE");
        logger.info("=================================================");
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://www.google.com", "https://www.youtube.com", "http://www.amazon.com"})
    @DisplayName("Deve gerar uma URL curta a partir de uma url longa")
    void testGenerateShortUrl(String longUrl){
        logger.info("Parâmetro de teste: URL Original = {}", longUrl);
        String shortUrl =  urlShortenerService.encurtadorUrl(longUrl);
        logger.info("URL Original -> {} | URL Encurtada -> {}", longUrl, shortUrl);
        assertTrue(urlMappingRepository.findByShortKey(shortUrl).isPresent(),
                "A chave curta '" + shortUrl + "' deveria existir no banco de dados, mas não foi encontrada.");
        logger.info("VALIDADO: A chave '{}' foi encontrada no banco de dados.", shortUrl);

    }


    @ParameterizedTest
    @ValueSource(strings = {"https://www.google.com", "https://www.youtube.com", "http://www.amazon.com"})
    @DisplayName("Deve transformar a urlEncurtada em uma Url Longa")
    void testGetUrlbyShortUrl(String longUrl){
        logger.info("Parâmetro de teste: URL Original = {}", longUrl);

        // Passo 1: Primeiro, encurtamos a URL para ter uma chave válida para buscar
        String shortKey = urlShortenerService.encurtadorUrl(longUrl);
        logger.info("URL '{}' foi encurtada para a chave '{}'.", longUrl, shortKey);

        // Passo 2: Agora, usamos a chave gerada para buscar a URL longa
        String retrievedLongUrl = urlShortenerService.obterUrlLonga(shortKey);
        logger.info("Buscando pela chave '{}'... URL Longa retornada: '{}'", shortKey, retrievedLongUrl);

        assertEquals(longUrl, retrievedLongUrl, "A URL longa retornada não corresponde à original.");
        logger.info("VALIDADO: A URL longa retornada é a correta.");

    }

    @ParameterizedTest
    @ValueSource(strings = "http://localhost:8080/dsada")
    @DisplayName("Validação de Exceção UrlNotFoundException")
    void testErroUrlNaoEncontrada(String shortUrl){
        logger.info("Parâmetro de teste: URL encurtada = {}",shortUrl);

        logger.info("Validando tratamento de erro quando a URL encurtada {} não é encontrada no banco de dados", shortUrl);
        UrlNotFoundException ex = Assertions.assertThrows(UrlNotFoundException.class,() -> urlShortenerService.obterUrlLonga(shortUrl));
        logger.info("Exceção computada: {}", ex.getMessage());
        logger.info("VALIDADO: A URL não foi encontrada no banco de dados");

    }

}
