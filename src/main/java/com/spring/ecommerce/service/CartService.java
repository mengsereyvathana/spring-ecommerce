package com.spring.ecommerce.service;

import com.spring.ecommerce.config.JwtAuthenticationFilter;
import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.entity.*;
import com.spring.ecommerce.enums.TokenType;
import com.spring.ecommerce.repository.CartItemRepository;
import com.spring.ecommerce.repository.CartRepository;
import com.spring.ecommerce.repository.ProductRepository;
import com.spring.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public Cart addToCart(Long productId, int quantity) throws IOException {
//        Product product = productRepository.findById(productId).orElseThrow(() -> new IOException("Product does not exist."));

//        String userEmail = JwtAuthenticationFilter.CURRENT_USER;
//        User user = userRepository.findByEmail(userEmail).orElseThrow();
//
//        Product existingProduct = productRepository.findById(productId).orElse(null);
//        if(existingProduct == null) {
//            throw new IOException("Product does not exist");
//        }
//
//        CartItem cartItem = new CartItem();
//
//
//        Cart cart = new Cart();
//
//
//        return cartRepository.save(cart);

        Product product = productRepository.findById(productId).orElseThrow(() -> new IOException("Product does not exist."));
        String userEmail = JwtAuthenticationFilter.CURRENT_USER;
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Cart shoppingCart = user.getCart();

        if(shoppingCart == null){
            shoppingCart = new Cart();
        }
        List<CartItem> cartItemList = shoppingCart.getCartItems();
        CartItem cartItem = find(cartItemList, product.getId());

        double unitPrice = product.getPrice();

        System.out.println("hello owlrdddddddddddddddddddddddd ii" + unitPrice);

        int itemQuantity = 0;
        if (cartItemList == null) {
            cartItemList = new ArrayList<>();
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(shoppingCart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItemList.add(cartItem);
                cartItemRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
                cartItemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(shoppingCart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItemList.add(cartItem);
                cartItemRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
                cartItemRepository.save(cartItem);
            }
        }
        shoppingCart.setCartItems(cartItemList);

        double totalPrice = totalPrice(shoppingCart.getCartItems());
        int totalItem = totalItem(shoppingCart.getCartItems());

        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setTotalItems(totalItem);
        shoppingCart.setUser(user);

        return cartRepository.save(shoppingCart);
    }

    private CartItem find(List<CartItem> cartItems, long productId) {
        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == productId) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    private Product transfer(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImage1(productDto.getImage1());
        product.setImage2(productDto.getImage2());
        product.setImage3(productDto.getImage3());
        product.setCategory(productDto.getCategory());
        return product;
    }

    private double totalPrice(List<CartItem> cartItemList) {
        double totalPrice = 0.0;
        for (CartItem item : cartItemList) {
            totalPrice += item.getUnitPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    private int totalItem(List<CartItem> cartItemList) {
        int totalItem = 0;
        for (CartItem item : cartItemList) {
            totalItem += item.getQuantity();
        }
        return totalItem;
    }
}
