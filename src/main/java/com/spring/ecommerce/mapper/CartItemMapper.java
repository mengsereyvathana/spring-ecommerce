package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.CartDto;
import com.spring.ecommerce.dto.CartItemDto;
import com.spring.ecommerce.dto.CategoryDto;
import com.spring.ecommerce.entity.Cart;
import com.spring.ecommerce.entity.CartItem;
import com.spring.ecommerce.entity.Category;

import java.util.HashMap;
import java.util.Map;

public class CartItemMapper {
    private static final Map<CartItem, CartItemDto> mappedCartItemDto = new HashMap<>();
    private static final Map<CartItemDto, CartItem> mappedCartItem = new HashMap<>();

    public static CartItemDto mapToCartItemDto(CartItem cartItem){
        if (cartItem == null) {
            return null;
        }
        if (mappedCartItemDto.containsKey(cartItem)) {
            return mappedCartItemDto.get(cartItem);
        }

        CartItemDto cartItemDto = new CartItemDto();
        mappedCartItemDto.put(cartItem, cartItemDto);

        cartItemDto.setId(cartItem.getId());
        cartItemDto.setCart(CartMapper.mapToCartDto(cartItem.getCart()));
        cartItemDto.setProduct(cartItem.getProduct());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setUnitPrice(cartItem.getUnitPrice());
        return cartItemDto;
    }
    public static CartItem mapToCartItem(CartItemDto cartItemDto){
        if (cartItemDto == null) {
            return null;
        }
        if (mappedCartItem.containsKey(cartItemDto)) {
            return mappedCartItem.get(cartItemDto);
        }

        CartItem cartItem = new CartItem();
        mappedCartItem.put(cartItemDto, cartItem);

        cartItem.setId(cartItemDto.getId());
        cartItem.setCart(CartMapper.mapToCart(cartItemDto.getCart()));
        cartItem.setProduct(cartItemDto.getProduct());
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setUnitPrice(cartItemDto.getUnitPrice());
        return cartItem;
    }
}
