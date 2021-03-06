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
    @DisplayName("????????? ?????? ?????????")
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
    @DisplayName("????????? ?????????")
    public void pageable() {
        this.createProducts();

        Page<Product> result = productRepository.findAll(PageRequest.of(0, 10));

        log.info("result type: " + result.getClass().getName()); // ??? ??? ?????????
        log.info("Total Page: " + result.getTotalPages()); // ??? ??? ?????????
        log.info("Total Count: " + result.getTotalElements()); // ?????? ??????
        log.info("Page Number: " + result.getNumber()); // ?????? ????????? ??????
        log.info("Page Size: " + result.getSize()); // ?????? ????????? ??????
        log.info("has next Page : " + result.hasNext()); // ?????? ????????? ?????? ??????
        log.info("get Content : " + result.getContent()); // ?????? ????????? ?????? ??????
    }

    @Test
    @DisplayName("?????????")
    public void productPage() {
        this.createProducts();

        Sort sort = Sort.by("price").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<Product> result = productRepository.findByCategory(Category.TOP, pageable);

        log.info("result : {}", result);
        log.info("Total Page: {}", result.getTotalPages()); // ??? ??? ?????????
        log.info("Total Count: {}", result.getTotalElements()); // ?????? ??????
        log.info("Page Number: {}", result.getNumber()); // ?????? ????????? ??????
        log.info("Page Size: {}", result.getSize()); // ?????? ????????? ??????
        log.info("has next Page : {}", result.hasNext()); // ?????? ????????? ?????? ??????
        log.info("get Content : {}", result.getContent()); //
        log.info("content type : {}", result.getContent().get(0).getProductImg());

    }

    @Test
    @DisplayName("??????")
    public void ??????() {
        createProducts();

        log.info("info : {}", productRepository.findTop4ByOrderByIdDesc());
    }

    @Test
    @DisplayName("?????? ????????? ?????? ??????")
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
    @DisplayName("?????? ?????? ????????? ?????? ??????")
    public void highPriceProducts() {

        this.createProducts();

        Sort sort = Sort.by("price").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);


        Page<Product> result = productRepository.findByCategory(Category.TOP, pageable);

        assertEquals(1100, result.getContent().get(0).getPrice());

        log.info("products : {}", result.getContent());
    }

    @Test
    @DisplayName("?????? ?????? ????????? ?????? ??????")
    public void lowPriceProducts() {
        this.createProducts();

        Sort sort = Sort.by("price").ascending();
        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<Product> result = productRepository.findByCategory(Category.TOP, pageable);

        assertEquals(1001, result.getContent().get(0).getPrice());

        log.info("products : {}", result.getContent());
    }

    @Test
    @DisplayName("?????? ?????? ?????? ????????? ??????")
    public void productByName() {
        this.createProducts();

        Sort sort = Sort.by("productName").ascending();
        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<Product> result = productRepository.findByCategory(Category.TOP, pageable);

        assertEquals("Sample1", result.getContent().get(0).getProductName());

        log.info("products : {}", result.getContent());
    }


}