package com.rudra.productservice.services;

import com.rudra.productservice.Exceptions.ProductNotExistsException;
import com.rudra.productservice.controllers.ProductController;
import com.rudra.productservice.dtos.msgBody;
import com.rudra.productservice.models.Product;
import org.apache.logging.log4j.message.StringFormattedMessage;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long id) throws ProductNotExistsException;

    List<Product> getAllProducts() throws ProductNotExistsException;

    Product addNewProduct(Product product);

    Product replaceProduct(Long id, Product product) throws ProductNotExistsException;

    Product updateProduct(Long id, Product product) throws ProductNotExistsException;

    Product deleteProduct(Long id) throws ProductNotExistsException;

    msgBody getBody(msgBody text);

}
