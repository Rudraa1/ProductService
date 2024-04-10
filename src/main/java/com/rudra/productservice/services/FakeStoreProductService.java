package com.rudra.productservice.services;

import com.rudra.productservice.Exceptions.ProductNotExistsException;
import com.rudra.productservice.dtos.FakeStoreProductDto;
import com.rudra.productservice.dtos.msgBody;
import com.rudra.productservice.models.Category;
import com.rudra.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/*
This is a demo on how to call third party APIs and
perform various actions, using HTTP methods, on that data when dealing with third party integrations.
*/

@Service("FakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private final RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    private Product convertFakeStoreProductToProduct(FakeStoreProductDto fakeStoreProduct){
        Product product = new Product();
        product.setTitle(fakeStoreProduct.getTitle());
        product.setId(fakeStoreProduct.getId());
        product.setPrice(fakeStoreProduct.getPrice());
        product.setDescription(fakeStoreProduct.getDescription());
        product.setImageUrl(fakeStoreProduct.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(fakeStoreProduct.getCategory());

        return product;

    }
    @Override
    public Product getSingleProduct(Long id) throws ProductNotExistsException {
        FakeStoreProductDto productDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class
        );

        //checking if product is present or not!
        if(productDto == null){
            throw new ProductNotExistsException(
                    "Product with id" + id + "doesn't exist."
            );
        }

        return convertFakeStoreProductToProduct(productDto);
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] productDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );

        List<Product> answer = new ArrayList<>();

        for (FakeStoreProductDto dto: productDto) {
            answer.add(convertFakeStoreProductToProduct(dto));
        }

        return answer;
    }

    @Override
    public Product addNewProduct(Product product) {
        Product newProduct = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                product,
                Product.class
        );

        return newProduct;

//        HttpEntity<Product> requestEntity = new HttpEntity<>(product);
//
//        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.exchange(
//                "https://fakestoreapi.com/products",
//                HttpMethod.POST,
//                requestEntity,
//                FakeStoreProductDto.class
//        );
//
//        FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();
//
//        return convertFakeStoreProductToProduct(fakeStoreProductDto);
    }

//    #fun fact: new.org.springframework..http.HttpMethod("Mohit") this is how you create a new http method
    @Override
    public Product replaceProduct(Long id, Product product) throws ProductNotExistsException {

        FakeStoreProductDto productDt = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class
        );

        //checking if product is present or not!
        if(productDt == null){
            throw new ProductNotExistsException(
                    "Product with id" + id + "doesn't exist."
            );
        }

        FakeStoreProductDto productDto = new FakeStoreProductDto();
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        productDto.setImage(product.getDescription());
        productDto.setImage(product.getImageUrl());

//        This code is from postForObject
//        RequestCallback requestCallback = httpEntityCallback(request, responseType);
//        HttpMessageConverterExtractor<T> responseExtractor =
//                new HttpMessageConverterExtractor<>(responseType, getMessageConverters(), logger);
//        return execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables);


        //Using a lower level method since PUT method doesn't allow us to take JSON, which will be provided by FakeStore
        RequestCallback requestCallback = restTemplate.httpEntityCallback(productDto, FakeStoreProductDto.class);

        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());

        FakeStoreProductDto response = restTemplate.execute(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT, requestCallback, responseExtractor
        );

        return convertFakeStoreProductToProduct(response);
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductNotExistsException {

        FakeStoreProductDto productDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class
        );

        //checking if product is present or not!
        if(productDto == null){
            throw new ProductNotExistsException(
                    "Product with id" + id + "doesn't exist."
            );
        }


        Product updatedProduct = restTemplate.patchForObject(
                "https://fakestoreapi.com/products/" + id,
                product,
                Product.class
        );

        return updatedProduct;

    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotExistsException {

        FakeStoreProductDto productDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class
        );

        //checking if product is present or not!
        if(productDto == null){
            throw new ProductNotExistsException(
                    "Product with id" + id + "doesn't exist."
            );
        }

        restTemplate.delete(
                "https://fakestoreapi.com/products/" + id
        );

        return convertFakeStoreProductToProduct(productDto);
    }

    @Override
    public msgBody getBody(msgBody text) {
        msgBody msg = restTemplate.postForObject(
                "http://localhost:3000/msgBody",
                null,
                msgBody.class
        );

        System.out.println(msg);
        return null;
    }


}
