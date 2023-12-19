package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.CartDto;
import com.spring.ecommerce.entity.Cart;

public class CartMapper {
    public static CartDto mapToCartDto(Cart cart){
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setCartItems(cart.getCartItems());
        cartDto.setTotalItems(cart.getTotalItems());
        cartDto.setTotalPrice(cart.getTotalPrice());
        return cartDto;
    }
    public static Cart mapToCart(CartDto cartDto){
        Cart cart = new Cart();
        cart.setId(cartDto.getId());
        cart.setCartItems(cartDto.getCartItems());
        cart.setTotalItems(cartDto.getTotalItems());
        cart.setTotalPrice(cartDto.getTotalPrice());
        return cart;
    }
}
