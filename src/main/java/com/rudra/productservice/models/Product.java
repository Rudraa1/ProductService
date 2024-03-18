package com.rudra.productservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel{

    private String title;
    private Double price;
    @ManyToOne
    @JsonBackReference
    private Category category;
    private String description;
    private String imageUrl;
}
