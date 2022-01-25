package com.shop.dto;

import com.shop.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDto {

    private Long id;
    private Product product;
    private String productImg;
    private int count;
}
