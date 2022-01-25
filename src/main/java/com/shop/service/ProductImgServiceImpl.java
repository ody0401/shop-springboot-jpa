package com.shop.service;

import com.shop.dto.ProductRegisterRequestDto;
import com.shop.entity.Product;
import com.shop.entity.ProductImg;
import com.shop.repository.ProductImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductImgServiceImpl implements ProductImgService {

    private final ProductImgRepository productImgRepository;

    @Override
    public Long register(ProductRegisterRequestDto dto, Product product) {


        ProductImg productImg = ProductImg.builder()
                .oriImgName(dto.getPicture().getOriginalFilename())
                .imgName(dto.getPictureUrl())
                .product(product)
                .build();

        ProductImg savedProductImg = productImgRepository.save(productImg);


        return savedProductImg.getProduct().getId();
    }

}
