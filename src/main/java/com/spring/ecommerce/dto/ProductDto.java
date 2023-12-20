package com.spring.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring.ecommerce.entity.Category;
import com.spring.ecommerce.entity.ProductDetail;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;

    private @NotNull String name;
    private @NotNull double price;
    private @NotNull String description;
    private @NotNull int quantity;

    private String image1;
    private String image2;
    private String image3;

    private @NotNull ProductDetailDto detail;

    @JsonIgnoreProperties("products")
    private @NotNull CategoryDto category;
}
