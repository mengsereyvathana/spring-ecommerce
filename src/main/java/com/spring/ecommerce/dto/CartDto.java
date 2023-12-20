package com.spring.ecommerce.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring.ecommerce.entity.CartItem;
import com.spring.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long id;

    @JsonManagedReference
    private List<CartItemDto> cartItems;

    private double totalPrice;
    private int totalItems;

    //    private User user;
}
