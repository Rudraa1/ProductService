package com.rudra.productservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msg")
public class MessageController {

    @PostMapping("/process-message")
    public String processMessage(@RequestBody String messageContent) {
        // Process message content
        // Perform your business logic here
        System.out.println("Received message from WhatsApp: " + messageContent);
        return "Message processed successfully";
    }
}

