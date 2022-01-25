package com.shop.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CartAddRequestDto {

    @Min(value = 1, message = "수량이 없습니다.")
    private int count;

    @NotNull(message = "상품이 없습니다.")
    private Long productId;

    private String userId;
}
