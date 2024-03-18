package com.rudra.productservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//This class will have multiple methods that will be serving HTTP Requests at /hello
//This class will be serving Rest (HTTP) APIS
//localhost:8080/hello
@RestController //This annotation will create a BEAN
@RequestMapping("/webhook")//This is the route
public class  HelloController {

    //When someone will say GET request at /hello/ this method will be called
    @GetMapping("/{name}")
    public String sayHello(@PathVariable("name") String name){
        return "Hello there" + " " + name;
    }

}
