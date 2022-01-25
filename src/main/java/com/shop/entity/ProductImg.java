package com.shop.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "product")
@Entity
@Table(name = "product_img")
public class ProductImg extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_no")
    private Long id;

    private String imgName; //이미지 파일명

    private String oriImgName; //원본 이미지 파일명


    @OneToOne
    @JoinColumn(name = "product_no")
    private Product product;
}
