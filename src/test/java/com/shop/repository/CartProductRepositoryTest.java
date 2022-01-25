package com.shop.repository;

import com.shop.constant.Category;
import com.shop.constant.ItemSellStatus;
import com.shop.constant.Role;
import com.shop.entity.Cart;
import com.shop.entity.CartProduct;
import com.shop.entity.Member;
import com.shop.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
public class CartProductRepositoryTest {

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

        this.member = memberRepository.save(member);

        Cart cart = Cart.builder()
                .member(member)
                .build();

        this.cart = cartRepository.save(cart);

        Product product = Product.builder()
                .productName("SAMPLE")
                .price(1000)
                .productDetail("SAMPLE PRODUCT")
                .stockNumber(10)
                .productSellStatus(ItemSellStatus.SELL)
                .category(Category.BOTTOM)
                .build();

        this.product = productRepository.save(product);

        CartProduct cartProduct = CartProduct.builder()
                .cart(cart)
                .product(product)
                .count(2)
                .build();
        this.cartProduct = cartProductRepository.save(cartProduct);

    }


    @Test
    @DisplayName("카트 추가")
    public void 카트추가() {

        createAndAddCart();
        log.info("cart Product : {}", cartProduct);

    }

    @Test
    @Transactional
    @DisplayName("회원 카트 조회")
    public void 카트조회() {

        createAndAddCart();

        Member member = memberRepository.findByUserId("test1@test.com");
        log.info("member : {}", member);
        Cart cart = cartRepository.findByMember(member);
        log.info("cart : {}", cart);
        List<CartProduct> cartProduct = cartProductRepository.findByCart(cart);
        log.info("cartProduct : {}", cartProduct);
        log.info("Product : {}", cartProduct.get(0).getProduct());
    }
}
