package com.shop.entity;

import com.shop.constant.Category;
import com.shop.constant.ItemSellStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "productImg")
@Entity
@Table(name = "product")
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no")
    private Long id;

    @Column(nullable = false, length = 50)
    private String productName; // 상품명

    @Column(nullable = false)
    private int price; // 상품 가격
    
    @Column(nullable = false)
    private String productDetail; // 상품 설명

    @Column(nullable = false)
    private int stockNumber; // 재고수량

    @Enumerated(EnumType.STRING)
    private ItemSellStatus productSellStatus; // 상품 판매 상태

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductImg productImg;



    @Enumerated(EnumType.STRING)
    private Category category; // 상품 카테고리
}
