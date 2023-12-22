package com.spring.ecommerce.mapper;

import com.spring.ecommerce.dto.CartDto;
import com.spring.ecommerce.dto.CategoryDto;
import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.entity.Cart;
import com.spring.ecommerce.entity.Category;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CartMapper {

    private static final Map<Cart, CartDto> mappedCartDto = new HashMap<>();
    private static final Map<CartDto, Cart> mappedCart = new HashMap<>();
    public static CartDto mapToCartDto(Cart cart){
        if (cart == null) {
            return null;
        }
        if (mappedCartDto.containsKey(cart)) {
            return mappedCartDto.get(cart);
        }

        CartDto cartDto = new CartDto();
        mappedCartDto.put(cart, cartDto);

        cartDto.setId(cart.getId());
        cartDto.setCartItems(cart.getCartItems() != null ? cart.getCartItems().stream()
                .map(CartItemMapper::mapToCartItemDto)
                .collect(Collectors.toList()) : null);
        cartDto.setTotalItems(cart.getTotalItems());
        cartDto.setTotalPrice(cart.getTotalPrice());
        return cartDto;
    }
    public static Cart mapToCart(CartDto cartDto){
        if (cartDto == null) {
            return null;
        }
        if (mappedCart.containsKey(cartDto)) {
            return mappedCart.get(cartDto);
        }

        Cart cart = new Cart();
        mappedCart.put(cartDto, cart);

        cart.setId(cartDto.getId());
        cart.setCartItems(cartDto.getCartItems() != null ? cartDto.getCartItems().stream()
                .map(CartItemMapper::mapToCartItem)
                .collect(Collectors.toList()) : null);
        cart.setTotalItems(cartDto.getTotalItems());
        cart.setTotalPrice(cartDto.getTotalPrice());
        return cart;
    }
}
