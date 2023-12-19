package com.spring.ecommerce.controller;

import com.spring.ecommerce.common.ApiResponse;
import com.spring.ecommerce.dto.CartDto;
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

    @GetMapping("/get-cart-by-username")
    public ResponseEntity<ApiResponse<CartDto>> getCartByUsername(){
        return new ResponseEntity<>(new ApiResponse<>(true, "Cart of user", cartService.getCartByUsername()), HttpStatus.OK);
    }

    @PostMapping("/add-to-cart/{productId}")
    public ResponseEntity<ApiResponse<CartDto>> addToCart(@PathVariable(name = "productId") Long productId,  @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity) throws IOException {
        ProductDto productDto = productService.getById(productId);
        int cartQuantity = cartService.getCartQuantity(productDto);
        if(cartQuantity == 0){
            return new ResponseEntity<>(new ApiResponse<>(true, "Added to cart", cartService.addToCart(productDto, quantity)), HttpStatus.CREATED);
        }
        if(cartQuantity >= productDto.getQuantity()) {
            return new ResponseEntity<>(new ApiResponse<>(true, "Product stock is limited", null), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ApiResponse<>(true, "Added to cart", cartService.addToCart(productDto, quantity)), HttpStatus.CREATED);
    }

    @PostMapping("/minus-from-cart/{productId}")
    public ResponseEntity<ApiResponse<CartDto>> minusFromCart(@PathVariable(name = "productId") Long productId,  @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity) throws IOException {
        ProductDto productDto = productService.getById(productId);
        int cartQuantity = cartService.getCartQuantity(productDto);
        if(cartQuantity == 1){
            return new ResponseEntity<>(new ApiResponse<>(true, "No item remove from cart.", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>(true, "Removed 1 quantity from item", cartService.minusFromCart(productDto, quantity)), HttpStatus.OK);
    }

    @DeleteMapping("/remove-cart-item/{productId}")
    public ResponseEntity<ApiResponse<String>> removeItemFromCart(@PathVariable(name = "productId") Long productId) throws IOException {
        ProductDto productDto = productService.getById(productId);
        return new ResponseEntity<>(new ApiResponse<>(true, "Item is removed from cart", cartService.removeItemFromCart(productDto)), HttpStatus.OK);
    }

    @PutMapping("/update-cart-item/{productId}")
    public ResponseEntity<ApiResponse<CartDto>> updateCartItem(@PathVariable(name = "productId") Long productId, @RequestParam int quantity) throws IOException {
        ProductDto productDto = productService.getById(productId);
        if(productDto.getQuantity() < quantity){
            return new ResponseEntity<>(new ApiResponse<>(true, "Quantity is limited", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>(true, "Cart successfully updated", cartService.updateCartItem(productDto, quantity)), HttpStatus.OK);
    }

    @DeleteMapping("/delete-cart")
    public ResponseEntity<ApiResponse<String>> deleteCart() {
        return new ResponseEntity<>(new ApiResponse<>(true, "Cart is successfully deleted", cartService.deleteCartByUsername()), HttpStatus.OK);
    }
}
