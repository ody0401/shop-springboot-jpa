package com.shop.entity;

import com.shop.constant.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryLabel {

    private Category category;
    private String label;
}

