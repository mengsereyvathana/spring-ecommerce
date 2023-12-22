package com.spring.ecommerce.dto;

import com.spring.ecommerce.entity.Order;
import com.spring.ecommerce.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private Long id;
    private Order order;
    private Product product;
}
