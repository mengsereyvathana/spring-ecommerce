package com.spring.ecommerce.dto;

import com.spring.ecommerce.entity.OrderDetail;
import com.spring.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private double totalPrice;
    private int quantity;
    private String paymentMethod;
    private String orderStatus;
    private LocalDateTime deliveryDate;
    private boolean isAccept;
    private User user;
    private List<OrderDetail> orderDetails;
}
