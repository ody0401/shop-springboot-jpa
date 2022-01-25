package com.shop.service;

import com.shop.dto.ProductRegisterRequestDto;
import com.shop.entity.Product;
import com.shop.entity.ProductImg;

public interface ProductImgService {

    public Long register(ProductRegisterRequestDto dto, Product product);

}
