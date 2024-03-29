package com.rudra.productservice.Repositories;

import com.rudra.productservice.models.Category;
import com.rudra.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
