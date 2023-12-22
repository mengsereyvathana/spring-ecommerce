package com.spring.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", orphanRemoval = true)
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private List<CartItem> cartItems;

    @OneToOne
    @JsonBackReference
    private User user;

    private double totalPrice;
    private int totalItems;

//    @Override
//    public int hashCode() {
//        return Objects.hash(id, cartItems, totalPrice, totalItems);
//    }
}
