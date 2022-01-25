package com.shop.service;

import com.shop.dto.CartAddRequestDto;
import com.shop.dto.CartDto;
import com.shop.entity.CartProduct;
import com.shop.entity.Product;
import com.shop.entity.ProductImg;

import java.util.List;

public interface CartService {

    void addCart(CartAddRequestDto dto);

    List<CartDto> cartList(String userId);

    void delete(Long id);

    default CartDto entityToDto(CartProduct cartProduct) {

        Product product = cartProduct.getProduct();
        ProductImg img = product.getProductImg();

        return CartDto.builder()
                .product(product)
                .productImg(img.getImgName())
                .count(cartProduct.getCount())
                .id(cartProduct.getId())
                .build();
    }
}
