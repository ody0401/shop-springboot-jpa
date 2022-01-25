package com.shop.service;

import com.shop.constant.Category;
import com.shop.constant.ItemSellStatus;
import com.shop.dto.PageRequestDto;
import com.shop.dto.PageResultDto;
import com.shop.dto.ProductDto;
import com.shop.dto.ProductRegisterRequestDto;
import com.shop.entity.Product;
import com.shop.entity.ProductImg;
import com.shop.repository.ProductDslRepository;
import com.shop.repository.ProductImgRepository;
import com.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductImgRepository productImgRepository;

    private final ProductDslRepository productDslRepository;

    private final ProductImgService productImgService;

    @Override
    public boolean register(ProductRegisterRequestDto dto) {

        Product product = Product.builder()
                .productName(dto.getProductName())
                .price(dto.getProductPrice())
                .productDetail(dto.getProductDescription())
                .stockNumber(dto.getProductStockNumber())
                .productSellStatus(ItemSellStatus.SELL)
                .category(dto.getCategory())
                .build();

        Product savedProduct = productRepository.save(product);

        Long imgProductId = productImgService.register(dto, savedProduct);

        return savedProduct.getId() == imgProductId;

    }


    @Override
    public PageResultDto<ProductDto, Product> getProductList(PageRequestDto dto) {

        Page<Product> result = productRepository.findByCategory(dto.getCategoryType(), dto.getPageable());

        if (result.getTotalElements() == 0) {
            throw new NotFoundException("등록 된 상품이 없습니다.");
        }


        Function<Product, ProductDto> fn = (this::entityToDto);

        return new PageResultDto<>(result, fn);

    }

    @Override
    public ProductDto findProduct(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if (!product.isPresent()) {
            throw new NotFoundException("상품을 찾지 못했습니다.");
        }

        return entityToDto(product.get());
    }

    @Override
    public List<Product> newProduct() {

        return productRepository.findTop4ByOrderByIdDesc();
    }

    @Override
    public List<Product> search(String keyword) {
        return productDslRepository.getSearchProduct(keyword);
    }

    @Override
    public void dummies() {
        IntStream.rangeClosed(1, 9).forEach(i -> {

            Product product = Product.builder()
                    .productName("SAMPLE TOP 0" + i)
                    .stockNumber(10)
                    .productDetail("SAMPLE TOP 0" + i)
                    .price(1000 * i)
                    .productSellStatus(ItemSellStatus.SELL)
                    .category(Category.TOP)
                    .build();
            Product savedProduct = productRepository.save(product);

            ProductImg productImg = ProductImg.builder()
                    .imgName("pexels-wendy-wei-1656684.jpg")
                    .oriImgName("pexels-wendy-wei-1656684.jpg")
                    .product(savedProduct)
                    .build();

            productImgRepository.save(productImg);
        });

        IntStream.rangeClosed(10, 19).forEach(i -> {

            Product product = Product.builder()
                    .productName("SAMPLE OUTER " + i)
                    .stockNumber(10)
                    .productDetail("SAMPLE OUTER " + i)
                    .price(1000 * i)
                    .productSellStatus(ItemSellStatus.SELL)
                    .category(Category.OUTER)
                    .build();
            Product savedProduct = productRepository.save(product);

            ProductImg productImg = ProductImg.builder()
                    .imgName("pexels-cottonbro-7653702.jpg")
                    .oriImgName("pexels-cottonbro-7653702.jpg")
                    .product(savedProduct)
                    .build();

            productImgRepository.save(productImg);
        });

        IntStream.rangeClosed(20, 29).forEach(i -> {

            Product product = Product.builder()
                    .productName("SAMPLE SHIRTS " + i)
                    .stockNumber(10)
                    .productDetail("SAMPLE SHIRTS " + i)
                    .price(1000 * i)
                    .productSellStatus(ItemSellStatus.SELL)
                    .category(Category.SHIRTS)
                    .build();
            Product savedProduct = productRepository.save(product);

            ProductImg productImg = ProductImg.builder()
                    .imgName("pexels-cottonbro-6566834.jpg")
                    .oriImgName("pexels-cottonbro-6566834.jpg")
                    .product(savedProduct)
                    .build();

            productImgRepository.save(productImg);
        });

        IntStream.rangeClosed(30, 39).forEach(i -> {

            Product product = Product.builder()
                    .productName("SAMPLE BOTTOM " + i)
                    .stockNumber(10)
                    .productDetail("SAMPLE BOTTOM " + i)
                    .price(1000 * i)
                    .productSellStatus(ItemSellStatus.SELL)
                    .category(Category.BOTTOM)
                    .build();
            Product savedProduct = productRepository.save(product);

            ProductImg productImg = ProductImg.builder()
                    .imgName("white-gc284229e7_1280.jpg")
                    .oriImgName("white-gc284229e7_1280.jpg")
                    .product(savedProduct)
                    .build();

            productImgRepository.save(productImg);
        });

        IntStream.rangeClosed(40, 49).forEach(i -> {

            Product product = Product.builder()
                    .productName("SAMPLE SHOES " + i)
                    .stockNumber(10)
                    .productDetail("SAMPLE SHOES " + i)
                    .price(1000 * i)
                    .productSellStatus(ItemSellStatus.SELL)
                    .category(Category.SHOES)
                    .build();
            Product savedProduct = productRepository.save(product);

            ProductImg productImg = ProductImg.builder()
                    .imgName("pexels-aidan-jarrett-718981.jpg")
                    .oriImgName("pexels-aidan-jarrett-718981.jpg")
                    .product(savedProduct)
                    .build();

            productImgRepository.save(productImg);
        });
    }

}
