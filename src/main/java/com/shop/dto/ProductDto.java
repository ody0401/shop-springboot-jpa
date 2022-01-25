package com.shop.dto;

import com.shop.constant.Category;
import com.shop.constant.ItemSellStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    private Long productId;

    private String productName;

    private String productDetail;

    private int price;

    private Category category;

    private int stockNumber;

    private ItemSellStatus status;

    private String productImgUrl;


}
