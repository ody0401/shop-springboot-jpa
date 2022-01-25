package com.shop.prop;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ShopProperties {

    @Value("${upload.path}")
    private String uploadPath;
}
