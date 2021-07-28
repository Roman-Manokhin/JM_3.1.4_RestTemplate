package ru.rmanokhin.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.rmanokhin.rest.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class RestTemplateCRUD {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final String URL = "http://91.241.64.178:7081/api/users";

    @Autowired
    public RestTemplateCRUD(org.springframework.web.client.RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        //получаем печеньки, что бы использовать одну и туже сессию во всех запросах
        this.httpHeaders.set("Cookie",
                String.join(";", Objects.requireNonNull(restTemplate.headForHeaders(URL).get("Set-Cookie"))));
    }

    //Получаем из body всех юзеров
    public List<User> getAllUsers(){
        //Отправляем GET запрос что бы получить данные по URL
        //С помощью ParameterizedTypeReference указываем какие данные нам должны вернуть
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<User>>() {});
        return responseEntity.getBody();
    }
    //ResponseEntity возвращает нам ответ от сервера к которому мы обратились
    public ResponseEntity<String> addUser(){
        User newUser = new User(3L, "James", "Brown", (byte) 25);
        //Создаём запрос в который передаём пользователя для добавления
        //и передаём headers которые получили ранее, что бы сессия была одна
        HttpEntity<User> httpEntity = new HttpEntity<>(newUser, httpHeaders);
        //выполняем POST запрос для отправки и записи данных
        return restTemplate.postForEntity(URL, httpEntity, String.class);
    }

    public ResponseEntity<String> updateUser(){
        User updatedUser = new User(3L, "Thomas", "Shelby", (byte) 25);
        HttpEntity<User> httpEntity = new HttpEntity<>(updatedUser, httpHeaders);
        //выполняем PUT запрос так же передавая body с новым пользователем и тем же header
        //+ id пользователя которого мы заменяем
        return restTemplate.exchange(URL, HttpMethod.PUT, httpEntity, String.class, 3);
    }

    public ResponseEntity<String> deleteUser(){
        //Создаём переменную, в которую, сохраняем данные для передачи id пользователя которого удаляем
        Map<String, Long> uriVariables = new HashMap<>();
        //1 - id(которое передадим) т.к. url должен быть http://91.241.64.178:7081/api/users/{id}
        //2 - тот самый id, в перспективе передаём его просто в метод
        uriVariables.put("id", 3L);
        //body не передаём т.к. он не нужен, но передаём header для работы с той же сессией
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        return restTemplate.exchange(URL + "/{id}", HttpMethod.DELETE, entity, String.class, uriVariables);
    }

    public String answer(){
        return "Answer = " + addUser().getBody() + updateUser().getBody() + deleteUser().getBody();
    }


}
