package com.spring.ecommerce.controller;

import com.spring.ecommerce.common.ApiResponse;
import com.spring.ecommerce.config.JwtAuthenticationFilter;
import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.entity.Cart;
import com.spring.ecommerce.service.CartService;
import com.spring.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/admin/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    @PostMapping("/add-to-cart/{productId}")
    public ResponseEntity<ApiResponse<Cart>> addToCart(@PathVariable(name = "productId") Long productId,  @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity) throws IOException {
        return new ResponseEntity<>(new ApiResponse<>(true, "a new category created", cartService.addToCart(productId, quantity)), HttpStatus.CREATED);
    }

}
