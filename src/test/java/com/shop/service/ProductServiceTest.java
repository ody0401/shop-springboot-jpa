package com.shop.service;

import com.shop.constant.Category;
import com.shop.dto.PageRequestDto;
import com.shop.dto.PageResultDto;
import com.shop.dto.ProductDto;
import com.shop.dto.ProductRegisterRequestDto;
import com.shop.entity.Product;
import com.shop.repository.ProductDslRepository;
import com.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestConstructor;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class ProductServiceTest {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ProductDslRepository productDslRepository;

    @AfterEach
    void deleteAll(){
        productRepository.deleteAll();
    }

    private void createProducts() {
        IntStream.rangeClosed(1,101).forEach(i-> {
            String imageName = "image" + ".jpg";
            MockMultipartFile multipartFile =
                    new MockMultipartFile("file", imageName, "image/jpg", new byte[]{1,2,3,4});

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
    @DisplayName("검색")
    public void 검색() {
        createProducts();
        log.info("result : {}",productDslRepository.getSearchProduct("1"));
    }

    @Test
    @DisplayName("상품 등록 테스트")
    public void productRegisterTest() {
        //given
        String imageName = "image" + ".jpg";
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", imageName, "image/jpg", new byte[]{1,2,3,4});

        ProductRegisterRequestDto dto = new ProductRegisterRequestDto();
        dto.setProductName("Sample");
        dto.setProductPrice(1000);
        dto.setProductDescription("Sample detail");
        dto.setProductStockNumber(100);
        dto.setCategory(Category.TOP);
        dto.setPictureUrl(imageName);
        dto.setPicture(multipartFile);

        //when
        boolean register = productService.register(dto);
    }


    @Test
    @DisplayName("상품 리스트 가져오기")
    public void getProductsTest(){
        this.createProducts();

        PageRequestDto requestDto = PageRequestDto.builder()
                .category("top")
                .list("new")
                .page(2)
                .size(8)
                .build();

        log.info("request Dto : {}", requestDto);
        log.info("request Dto : {}", requestDto.getCategoryType());
        log.info("request Dto : {}", requestDto.getPageable());


        PageResultDto<ProductDto, Product> resultDto = productService.getProductList(requestDto);

        log.info("result Dto: {}", resultDto);

    }
    
    @Test
    @DisplayName("상품 정보 가져오기")
    public void getProductTest() {
        this.createProducts();
        Long id = 5l;

        ProductDto dto = productService.findProduct(id);

        log.info("dto : {}", dto);

        assertEquals(dto.getProductId(), id);
    }


}