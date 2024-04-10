package com.rudra.productservice.commons;

import com.rudra.productservice.dtos.UserDto;
import com.rudra.productservice.dtos.msgBody;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthCommons {

    private RestTemplate restTemplate;

    public AuthCommons(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public UserDto validateToken(String token){
        ResponseEntity<UserDto> userDtoResponse = restTemplate.postForEntity(
                "https://localhost:8181/users/validate" + token,
                null,
                UserDto.class
        );

        if(userDtoResponse.getBody() == null){
            return null;
        }

        return userDtoResponse.getBody();
    }

    public void getBody(msgBody body){
        restTemplate.postForEntity(
                "http://localhost:3000/msgBody",
                null,
                msgBody.class
        );
    }
}
