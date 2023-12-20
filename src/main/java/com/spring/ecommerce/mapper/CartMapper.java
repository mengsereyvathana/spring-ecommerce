package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.CartDto;
import com.spring.ecommerce.entity.Cart;

import java.util.stream.Collectors;

public class CartMapper {
    public static CartDto mapToCartDto(Cart cart){
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setCartItems(cart.getCartItems().stream()
                .map(CartItemMapper::mapToCartItemDto)
                .collect(Collectors.toList()));
        cartDto.setTotalItems(cart.getTotalItems());
        cartDto.setTotalPrice(cart.getTotalPrice());
        return cartDto;
    }
    public static Cart mapToCart(CartDto cartDto){
        Cart cart = new Cart();
        cart.setId(cartDto.getId());
        cart.setCartItems(cartDto.getCartItems().stream()
                .map(CartItemMapper::mapToCartItem)
                .collect(Collectors.toList()));
        cart.setTotalItems(cartDto.getTotalItems());
        cart.setTotalPrice(cartDto.getTotalPrice());
        return cart;
    }
}
