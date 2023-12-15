package com.spring.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    private @NotNull String name;
    private @NotNull String description;
    private @NotNull String imageURL;
}
