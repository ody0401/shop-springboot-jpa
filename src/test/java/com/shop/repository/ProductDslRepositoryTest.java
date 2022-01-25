package com.shop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.Category;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Product;
import com.shop.entity.QProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
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
class ProductDslRepositoryTest {

    private final ProductRepository productRepository;
    private final ProductDslRepository productDslRepository;

    @PersistenceContext
    EntityManager em;

    @AfterEach
    void deleteAll(){
        productRepository.deleteAll();
    }


    private void createProductList() {
        IntStream.rangeClosed(1,10).forEach(i-> {
            String imageName = "image" + ".jpg";
            MockMultipartFile multipartFile =
                    new MockMultipartFile("file", imageName, "image/jpg", new byte[]{1,2,3,4});

            Product product = Product.builder()
                    .productName("sample" + i)
                    .productSellStatus(ItemSellStatus.SELL)
                    .productDetail("sample" + i)
                    .stockNumber(10)
                    .price(100)
                    .category(Category.TOP)
                    .build();

            productRepository.save(product);
        });
    }

    @Test
    @DisplayName("Querydsl 조회테스트1")
    public void queryDslTest() {
        this.createProductList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct qProduct = QProduct.product;
        JPAQuery<Product> query = queryFactory.selectFrom(qProduct);

        List<Product> products = query.fetch();


    }

    @Test
    @DisplayName("Querydsl 조회테스트2")
    public void queryTest() {
        this.createProductList();
        log.info("products: {}",productDslRepository.getProductList());
    }

    @Test
    @DisplayName("등록 순으로 상품 조회")
    public void newProduct() {
        this.createProductList();

        List<Product> products = productDslRepository.getNewProduct(Category.TOP);

        log.info("products : {}", products);
    }

    @Test
    @DisplayName("높은 가격 순으로 상품 조회")
    public void highPriceProducts() {
        this.createProductList();

        List<Product> products = productDslRepository.getHighPriceProducts(Category.TOP);

        assertNotNull(products);
    }

    @Test
    @DisplayName("낮은 가격 순으로 상품 조회")
    public void lowPriceProducts() {
        this.createProductList();

        List<Product> products = productDslRepository.getLowPriceProducts(Category.TOP);

        assertNotNull(products);
    }

    @Test
    @DisplayName("상품 등록 이름 순으로 조회")
    public void productByName() {
        this.createProductList();

        List<Product> products = productDslRepository.getProductsByName(Category.TOP);

        assertNotNull(products);
    }

}