package com.rudra.productservice.Repositories;

import com.rudra.productservice.Repositories.Projections.ProductWIthIdAndTitle;
import com.rudra.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_Id(Long id);

    Optional<Product> findById(Long id);
    //this will return a null if no product is there

    Optional<List<Product>> findAllBy();

    Product save(Product product);

    @Query("select p.id as id, p.title as title from Product p where id = :id ")//This is a decleared query
    List<ProductWIthIdAndTitle> something(@Param("id") Long id);

    @Query(value = "select * from product p where p.id = 2", nativeQuery = true)//This is a SQL query, this is not HQL or ORM method
    List<Product> some2();
}
