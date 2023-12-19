package com.spring.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spring.ecommerce.entity.Cart;
import com.spring.ecommerce.entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long id;
    @JsonBackReference
    private Cart cart;
    private Product product;
    private Integer quantity;
    private double unitPrice;
}
