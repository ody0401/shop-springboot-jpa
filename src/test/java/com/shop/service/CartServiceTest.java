package com.shop.service;

import com.shop.constant.Category;
import com.shop.constant.ItemSellStatus;
import com.shop.constant.Role;
import com.shop.entity.Cart;
import com.shop.entity.CartProduct;
import com.shop.entity.Member;
import com.shop.entity.Product;
import com.shop.repository.CartProductRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
class CartServiceTest {

    private final CartService cartService;

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;

    private final PasswordEncoder passwordEncoder;

    private Member member;
    private Product product;
    private Cart cart;
    private CartProduct cartProduct;

    private void createAndAddCart() {
        Member member = Member.builder().userId("test1@test.com")
                .userName("홍길동")
                .userPw(passwordEncoder.encode("1234"))
                .role(Role.USER)
                .build();

        memberRepository.save(member);

        Cart cart = Cart.builder()
                .member(member)
                .build();

        cartRepository.save(cart);

        IntStream.rangeClosed(1,10).forEach(i -> {

            Product product = Product.builder()
                    .productName("SAMPLE"+i)
                    .price(1000 + i)
                    .productDetail("SAMPLE PRODUCT")
                    .stockNumber(10)
                    .productSellStatus(ItemSellStatus.SELL)
                    .category(Category.BOTTOM)
                    .build();

            productRepository.save(product);

            CartProduct cartProduct = CartProduct.builder()
                    .cart(cart)
                    .product(product)
                    .count(i)
                    .build();
            cartProductRepository.save(cartProduct);

        });

    }

    @Test
    @DisplayName("그냥 테스트")
    public void 테스트() {

        createAndAddCart();
        cartService.cartList("test1@test.com");

    }

}