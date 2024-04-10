package com.rudra.productservice.controllers;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.rudra.productservice.Exceptions.ProductNotExistsException;
import com.rudra.productservice.commons.AuthCommons;
import com.rudra.productservice.dtos.Role;
import com.rudra.productservice.dtos.UserDto;
import com.rudra.productservice.dtos.msgBody;
import com.rudra.productservice.models.Product;
import com.rudra.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private AuthCommons authCommons;

    //Constructor
    @Autowired
    public ProductController(@Qualifier("FakeStoreProductService") ProductService productService, AuthCommons authCommons){
        this.authCommons = authCommons;
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(@RequestHeader String token) throws ProductNotExistsException {

        UserDto userDto = authCommons.validateToken(token);

        if( userDto == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        };

        boolean isAdmin = false;

        for(Role role: userDto.getRole()){
           if(role.getName().equals("admin")){
               isAdmin=true;
               break;
           }
        }

        if(!isAdmin) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        ResponseEntity<List<Product>> response = new ResponseEntity<>(
                productService.getAllProducts(), HttpStatus.OK
        );

        return response;
    }

    @GetMapping("/{id}")
    public Product getSingleProduct(@PathVariable("id") Long id ) throws ProductNotExistsException {

        return productService.getSingleProduct(id);
    }


    @PostMapping()
    public Product addNewProduct(@RequestBody Product product){
        return productService.addNewProduct(product);
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotExistsException {
        return productService.updateProduct(id, product);
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotExistsException {
        return productService.replaceProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable("id") Long id) throws ProductNotExistsException {
        return productService.deleteProduct(id);

    }

    @PostMapping("/getMsg")
    public msgBody getMsg(@RequestBody msgBody msgBody){

        return productService.getBody(msgBody);
    }

    @PostMapping("/process-message")
    public String processMessage(@RequestBody String messageContent) {
        // Process message content
        // Perform your business logic here
        System.out.println("Received message from WhatsApp: " + messageContent);
        return "Message processed successfully";
    }


}
