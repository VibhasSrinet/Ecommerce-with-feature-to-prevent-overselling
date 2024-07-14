package org.example.productcatalogservice_may2024.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product extends BaseModel {

    private Boolean isPrime;

    private String name;

    private String description;

    private String imageUrl;

    private Double price;

    private Integer quantity;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    @Version
    private Long version = 0L;

    //private Boolean isPrime;
}