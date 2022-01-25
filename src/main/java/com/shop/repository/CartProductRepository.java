package com.shop.repository;

import com.shop.entity.Cart;
import com.shop.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    List<CartProduct> findByCart(Cart cart);
}
