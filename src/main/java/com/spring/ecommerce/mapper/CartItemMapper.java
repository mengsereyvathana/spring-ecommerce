package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.CartDto;
import com.spring.ecommerce.dto.CartItemDto;
import com.spring.ecommerce.entity.Cart;
import com.spring.ecommerce.entity.CartItem;

public class CartItemMapper {
    public static CartItemDto mapToCartItemDto(CartItem cartItem){
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(cartItem.getId());
        cartItemDto.setCart(CartMapper.mapToCartDto(cartItem.getCart()));
        cartItemDto.setProduct(cartItem.getProduct());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setUnitPrice(cartItem.getUnitPrice());
        return cartItemDto;
    }
    public static CartItem mapToCartItem(CartItemDto cartItemDto){
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDto.getId());
        cartItem.setCart(CartMapper.mapToCart(cartItemDto.getCart()));
        cartItem.setProduct(cartItemDto.getProduct());
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setUnitPrice(cartItemDto.getUnitPrice());
        return cartItem;
    }
}
