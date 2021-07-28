package ru.rmanokhin.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
@ComponentScan("ru.rmanokhin.rest")
public class Config {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    //Получаем headers для дальнешего использования
    //и настраиваем на использование с JSONом
    @Bean
    public HttpHeaders httpHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }


}
