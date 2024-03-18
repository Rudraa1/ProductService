package com.rudra.productservice;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.rudra.productservice.Repositories.ProductRepository;
import com.rudra.productservice.Repositories.Projections.ProductWIthIdAndTitle;
import com.rudra.productservice.models.Product;
import jakarta.transaction.Transactional;
import org.apache.catalina.LifecycleState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.beans.Transient;
import java.util.List;

@SpringBootTest
class ProductserviceApplicationTests {


    @Autowired
    private ProductRepository productRepository;
    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    @Commit//no data rolling back
    void testQueries(){
        List<ProductWIthIdAndTitle> products = productRepository.something(101L);

            for(ProductWIthIdAndTitle product: products){
                System.out.println(product.getId());
                System.out.println(product.getTitle());
            }

            List<Product> products1 = productRepository.some2();

    }

}
