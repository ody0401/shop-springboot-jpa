package com.shop.dto;

import com.shop.constant.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductRegisterRequestDto {

    @NotBlank(message = "상품명을 입력해주세요.")
    private String productName;

    @NotNull(message = "상품가격을 입력해주세요.")
    private int productPrice;

    @NotNull(message = "재고 수량을 입력해주세요.")
    private int productStockNumber;

    @NotBlank(message = "상세 내용을 입력해주세요.")
    private String productDescription;

    @NotNull(message = "상품 이미지를 선택해주세요.")
    private MultipartFile picture;

    private String pictureUrl;
    private Category category;

}
