package com.shop.service;

import com.shop.constant.Category;
import com.shop.dto.PageRequestDto;
import com.shop.dto.PageResultDto;
import com.shop.dto.ProductDto;
import com.shop.dto.ProductRegisterRequestDto;
import com.shop.entity.Product;

import java.util.ArrayList;
import java.util.List;

public interface ProductService {

    boolean register(ProductRegisterRequestDto dto);

    PageResultDto<ProductDto, Product> getProductList(PageRequestDto dto);

    ProductDto findProduct(Long id);

    List<Product> newProduct();

    List<Product> search(String keyword);

    void dummies();

    default ProductDto entityToDto(Product product) {


        ProductDto dto = ProductDto.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .category(product.getCategory())
                .productDetail(product.getProductDetail())
                .stockNumber(product.getStockNumber())
                .status(product.getProductSellStatus())
                .productImgUrl(product.getProductImg().getImgName())
                .build();
        return dto;
    }


}
