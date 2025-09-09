package com.jfelixy.encurtadorurl.service;

import com.jfelixy.encurtadorurl.model.UrlMapping;
import com.jfelixy.encurtadorurl.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlMappingRepository repository;


    private static final String BASE62_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


    private static final long ID_OFFSET = 1_000_000_000L;

    /**
     * Salva a Url no banco, depois captura seu id e o codifica em base62, sendo utilizado
     * posteriormente como shortUrl
     *
     * @param longUrl
     * @return Retorna a url encurtada a partir da transformação do id para base64, o que torna o valor unico, sem
     * necessidade de verificar todas as url em busca de uma igual.
     */
    @Transactional
    public String encurtadorUrl(String longUrl)  {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(longUrl);
        InetAddress infoIp;
        try {
             infoIp = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        UrlMapping urlSalva = repository.save(urlMapping);

        Long id = urlSalva.getId();

        String shortKey =  infoIp.getHostAddress() + "/"+ base62Encode(id + ID_OFFSET);

        urlSalva.setShortKey(shortKey);
        repository.save(urlSalva);

        return shortKey;
    }

    /**
     *  Método para obter a url longa através da url em base64.
     * @param urlEncurtada
     * @return Obtém a url longa através da shortUrl
     */
    public String obterUrlLonga(String urlEncurtada){
        return repository.findByShortKey(urlEncurtada)
                .map(UrlMapping::getLongUrl).orElseThrow(() -> new RuntimeException("Url não encontrada"));
    }
    /**
     * Converte um número (ID) para sua representação em Base62.
     * @param number o ID do banco de dados.
     * @return A string codificada em Base62.
     */
    /**
     * Converte um número (ID) para sua representação em Base62.
     * @param number o ID do banco de dados.
     * @return A string codificada em Base62.
     */
    private String base62Encode(long number) {
        if (number == 0) {
            return String.valueOf(BASE62_CHARS.charAt(0));
        }

        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            int remainder = (int) (number % 62);
            sb.append(BASE62_CHARS.charAt(remainder));
            number /= 62;
        }

        // O algoritmo constrói a string de trás para frente, então precisamos invertê-la.
        return sb.reverse().toString();
    }

}
