package com.shop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.Category;
import com.shop.constant.ItemSellStatus;
import com.shop.dto.ProductRegisterRequestDto;
import com.shop.entity.Product;
import com.shop.entity.QProduct;
import com.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestConstructor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class ProductRepositoryTest {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @AfterEach
    void deleteAll() {
        productRepository.deleteAll();
    }

    private void createProducts() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            String imageName = "image" + ".jpg";
            MockMultipartFile multipartFile =
                    new MockMultipartFile("file", imageName, "image/jpg", new byte[]{1, 2, 3, 4});

            ProductRegisterRequestDto dto = new ProductRegisterRequestDto();
            dto.setProductName("Sample" + i);
            dto.setProductPrice(1000 + i);
            dto.setProductDescription("Sample detail" + i);
            dto.setProductStockNumber(100);
            dto.setCategory(Category.TOP);
            dto.setPictureUrl(imageName);
            dto.setPicture(multipartFile);

            productService.register(dto);
        });
    }

    @Test
    @DisplayName("엔티티 저장 테스트")
    public void save() {
        Product product = Product.builder()
                .productName("sample")
                .productSellStatus(ItemSellStatus.SELL)
                .productDetail("sample")
                .stockNumber(10)
                .price(100)
                .category(Category.TOP)
                .build();

        Product savedProduct = productRepository.save(product);
        log.info("saved : {}",savedProduct);

       savedProduct.setStockNumber(product.getStockNumber() - 2);


        log.info("saved : {}",productRepository.save(savedProduct));

    }

    @Test
    @DisplayName("페이징 테스트")
    public void pageable() {
        this.createProducts();

        Page<Product> result = productRepository.findAll(PageRequest.of(0, 10));

        log.info("result type: " + result.getClass().getName()); // 총 몇 페이지
        log.info("Total Page: " + result.getTotalPages()); // 총 몇 페이지
        log.info("Total Count: " + result.getTotalElements()); // 전체 개수
        log.info("Page Number: " + result.getNumber()); // 현재 페이지 번호
        log.info("Page Size: " + result.getSize()); // 현재 페이지 번호
        log.info("has next Page : " + result.hasNext()); // 다음 페이지 존재 여부
        log.info("get Content : " + result.getContent()); // 다음 페이지 존재 여부
    }

    @Test
    @DisplayName("페이징")
    public void productPage() {
        this.createProducts();

        Sort sort = Sort.by("price").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<Product> result = productRepository.findByCategory(Category.TOP, pageable);

        log.info("result : {}", result);
        log.info("Total Page: {}", result.getTotalPages()); // 총 몇 페이지
        log.info("Total Count: {}", result.getTotalElements()); // 전체 개수
        log.info("Page Number: {}", result.getNumber()); // 현재 페이지 번호
        log.info("Page Size: {}", result.getSize()); // 현재 페이지 번호
        log.info("has next Page : {}", result.hasNext()); // 다음 페이지 존재 여부
        log.info("get Content : {}", result.getContent()); //
        log.info("content type : {}", result.getContent().get(0).getProductImg());

    }

    @Test
    @DisplayName("신상")
    public void 신상() {
        createProducts();

        log.info("info : {}", productRepository.findTop4ByOrderByIdDesc());
    }

    @Test
    @DisplayName("등록 순으로 상품 조회")
    public void newProduct() {
        this.createProducts();

        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(0, 10, sort);


        Page<Product> result = productRepository.findByCategory(Category.TOP, pageable);

        assertEquals(1L, result.getContent().get(0).getId());

        log.info("products : {}", result);
        log.info("products : {}", result.getContent());
    }

    @Test
    @DisplayName("높은 가격 순으로 상품 조회")
    public void highPriceProducts() {

        this.createProducts();

        Sort sort = Sort.by("price").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);


        Page<Product> result = productRepository.findByCategory(Category.TOP, pageable);

        assertEquals(1100, result.getContent().get(0).getPrice());

        log.info("products : {}", result.getContent());
    }

    @Test
    @DisplayName("낮은 가격 순으로 상품 조회")
    public void lowPriceProducts() {
        this.createProducts();

        Sort sort = Sort.by("price").ascending();
        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<Product> result = productRepository.findByCategory(Category.TOP, pageable);

        assertEquals(1001, result.getContent().get(0).getPrice());

        log.info("products : {}", result.getContent());
    }

    @Test
    @DisplayName("상품 등록 이름 순으로 조회")
    public void productByName() {
        this.createProducts();

        Sort sort = Sort.by("productName").ascending();
        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<Product> result = productRepository.findByCategory(Category.TOP, pageable);

        assertEquals("Sample1", result.getContent().get(0).getProductName());

        log.info("products : {}", result.getContent());
    }


}