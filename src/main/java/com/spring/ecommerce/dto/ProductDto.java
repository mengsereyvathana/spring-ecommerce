package com.spring.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.ecommerce.entity.Category;
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

    private String image1;
    private String image2;
    private String image3;

    private @NotNull ProductDetailDto detail;

    @JsonIgnore
    private Long categoryId;

    private @NotNull Category category;
}
