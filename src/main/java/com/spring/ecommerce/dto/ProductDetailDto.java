package com.spring.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDetailDto {
    private Long id;
    private @NotNull String color;
    private @NotNull String size;
}
