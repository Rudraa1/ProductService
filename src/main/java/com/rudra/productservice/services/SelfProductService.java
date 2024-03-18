package com.rudra.productservice.services;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.rudra.productservice.Exceptions.ProductNotExistsException;
import com.rudra.productservice.Repositories.CategoryRepository;
import com.rudra.productservice.Repositories.ProductRepository;
import com.rudra.productservice.dtos.FakeStoreProductDto;
import com.rudra.productservice.models.Category;
import com.rudra.productservice.models.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Service("selfProductService")
public class SelfProductService implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotExistsException {

        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()){
            throw new ProductNotExistsException("Product  with id: " + id + " doesn't exist");
        }

        Product product = productOptional.get();
        return product;
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotExistsException {
        List<Product> ans = productRepository.findAll();

        if(ans.isEmpty()){
            throw new ProductNotExistsException("No products");
        }
        return ans;
    }

    @Override
    public Product addNewProduct(Product product) {

        Category category = product.getCategory();
        if(category.getId() == null){
            Category savedCategory = categoryRepository.save(category);
        }

        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(Long id, Product product) throws ProductNotExistsException {
        Optional<Product> productOptional = productRepository.findById(id);

        Category category = product.getCategory();
        if(category.getId() == null){
            Category savedCategory = categoryRepository.save(category);
        }

        if(productOptional.isEmpty()){
            throw new ProductNotExistsException("Product  with id: " + id + " doesn't exist");
        }

        Product product1 = productOptional.get();

        product1.setTitle(product.getTitle());
        product1.setPrice(product.getPrice());
        product1.setCategory(product.getCategory());
        product1.setDescription(product.getDescription());
        product1.setImageUrl(product.getImageUrl());

        return productRepository.save(product1);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()) throw new RuntimeException();

        Product savedProduct = productOptional.get();

        if(product.getTitle() != null){
            savedProduct.setTitle(product.getTitle());
        }

        if(product.getDescription() != null){
            savedProduct.setDescription(product.getDescription());
        }

        if (product.getPrice() != null){
            savedProduct.setPrice(product.getPrice());
        }

        if(product.getImageUrl() != null){
            savedProduct.setPrice(product.getPrice());
        }
        return productRepository.save(savedProduct);
    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotExistsException {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()){
            throw new ProductNotExistsException("Product  with id: " + id + " doesn't exist");
        }

        Product p = productOptional.get();
        p.setDelete(true);

//        String response = "delete";

        return productRepository.save(p);
    }
}
