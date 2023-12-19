package com.spring.ecommerce.service;

import com.spring.ecommerce.config.JwtAuthenticationFilter;
import com.spring.ecommerce.dto.CartDto;
import com.spring.ecommerce.dto.ProductDto;
import com.spring.ecommerce.entity.*;
import com.spring.ecommerce.mapper.CartMapper;
import com.spring.ecommerce.mapper.CategoryMapper;
import com.spring.ecommerce.mapper.ProductMapper;
import com.spring.ecommerce.repository.CartItemRepository;
import com.spring.ecommerce.repository.CartRepository;
import com.spring.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public CartDto getCartByUsername(){

        String userEmail = JwtAuthenticationFilter.CURRENT_USER;
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        return cartToDto(user.getCart());
    }

    public CartDto addToCart(ProductDto productDto, int quantity) throws IOException {
        Product product = ProductMapper.mapToProduct(productDto);

        String userEmail = JwtAuthenticationFilter.CURRENT_USER;
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Cart cart = user.getCart();

        if(cart == null){
            cart = new Cart();
        }

        List<CartItem> cartItemList = cart.getCartItems();
        CartItem cartItem = find(cartItemList, productDto.getId());

        int itemQuantity = 0;
        double unitPrice = productDto.getPrice();

        if (cartItemList == null) {
            cartItemList = new ArrayList<>();
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(unitPrice);
            cartItemList.add(cartItem);
            cartItemRepository.save(cartItem);
        } else {
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(cart);
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

        cart.setCartItems(cartItemList);

        double totalPrice = totalPrice(cart.getCartItems());
        int totalItem = totalItem(cart.getCartItems());

        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItem);
        cart.setUser(user);

        Cart savedCart = cartRepository.save(cart);
        return CartMapper.mapToCartDto(savedCart);

//        return cartToDto(cartRepository.save(cart));
    }

    public CartDto minusFromCart(ProductDto productDto, int quantity) throws IOException {
        Product product = ProductMapper.mapToProduct(productDto);

        String userEmail = JwtAuthenticationFilter.CURRENT_USER;
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Cart shoppingCart = user.getCart();

        if(shoppingCart == null){
            shoppingCart = new Cart();
        }

        List<CartItem> cartItemList = shoppingCart.getCartItems();
        CartItem cartItem = find(cartItemList, productDto.getId());

        int itemQuantity = 0;
        double unitPrice = productDto.getPrice();

        if (cartItemList == null) {
            cartItemList = new ArrayList<>();
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(shoppingCart);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(unitPrice);
            cartItemList.add(cartItem);
            cartItemRepository.save(cartItem);
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
                itemQuantity = cartItem.getQuantity() - quantity;
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

        return cartToDto(cartRepository.save(shoppingCart));
    }

    public CartDto updateCartItem(ProductDto productDto, int quantity) {
        String userEmail = JwtAuthenticationFilter.CURRENT_USER;
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Cart cart = user.getCart();

        List<CartItem> cartItemList = cart.getCartItems();
        CartItem item = find(cartItemList, productDto.getId());

        item.setQuantity(quantity);
        cartItemRepository.save(item);

        cart.setCartItems(cartItemList);
        int totalItem = totalItem(cartItemList);
        double totalPrice = totalPrice(cartItemList);

        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItem);

        return cartToDto(cartRepository.save(cart));
    }

    public String removeItemFromCart(ProductDto productDto) {
        String userEmail = JwtAuthenticationFilter.CURRENT_USER;
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Cart cart = user.getCart();

        List<CartItem> cartItemList = cart.getCartItems();
        CartItem item = find(cartItemList, productDto.getId());

        cartItemList.remove(item);
        cartItemRepository.delete(item);

        double totalPrice = totalPrice(cartItemList);
        int totalItem = totalItem(cartItemList);

        cart.setCartItems(cartItemList);
        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItem);

        cartRepository.save(cart);

        return "Item is removed from cart";
    }

    @Transactional
    public String deleteCartByUsername() {
        String userEmail = JwtAuthenticationFilter.CURRENT_USER;
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Cart cart = user.getCart();

        if (cart != null) {
            cartItemRepository.deleteAll(cart.getCartItems());

            cart.getCartItems().clear();
            cart.setTotalPrice(0);
            cart.setTotalItems(0);

//            command below will also remove cart from database
//            user.setCart(null);
//            cartRepository.save(cart);

            return "Cart is deleted";
        } else {
            return "Cart does not exist";
        }
    }

    public CartItem find(List<CartItem> cartItems, long productId) {
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

    public int getCartQuantity(ProductDto productDto){
        String userEmail = JwtAuthenticationFilter.CURRENT_USER;
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Cart cart = user.getCart();

        if(cart == null){
            cart = new Cart();
        }
        List<CartItem> cartItemList = cart.getCartItems();
        CartItem cartItem = find(cartItemList, productDto.getId());

        if(cartItem == null){
            return 0;
        }
        return cartItem.getQuantity();
    }

    private Product dtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDescription(productDto.getDescription());
        product.setImage1(productDto.getImage1());
        product.setImage2(productDto.getImage2());
        product.setImage3(productDto.getImage3());
        product.setCategory(productDto.getCategory());
        return product;
    }

    private CartDto cartToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setCartItems(cart.getCartItems());
        cartDto.setId(cart.getId());
        cartDto.setTotalItems(cart.getTotalItems());
        cartDto.setTotalPrice(cart.getTotalPrice());
        return cartDto;
    }
}
