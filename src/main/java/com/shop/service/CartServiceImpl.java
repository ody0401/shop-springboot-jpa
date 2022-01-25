package com.shop.service;

import com.shop.dto.CartAddRequestDto;
import com.shop.dto.CartDto;
import com.shop.entity.Cart;
import com.shop.entity.CartProduct;
import com.shop.entity.Member;
import com.shop.entity.Product;
import com.shop.exception.CartProductNotFoundException;
import com.shop.exception.OverCountException;
import com.shop.repository.CartProductRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.MemberRepository;
import com.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CartServiceImpl implements CartService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    @Override
    public void addCart(CartAddRequestDto dto) {

        Member member = memberRepository.findByUserId(dto.getUserId());

        Product product = productRepository.findById(dto.getProductId()).orElseThrow(
                () -> new IllegalStateException("상품을 찾을 수 없습니다.")
        );

        int count = dto.getCount();
        int stock = product.getStockNumber();

        if (stock < count) {
            throw new OverCountException("구매할 수 있는 수량을 초과했습니다.");
        }

        Cart cart = cartRepository.findByMember(member);
        Cart savedCart;

        savedCart = Objects.requireNonNullElseGet(cart, () -> createCart(member));

        CartProduct cartProduct = CartProduct.builder()
                .cart(savedCart)
                .product(product)
                .count(dto.getCount())
                .build();

        cartProductRepository.save(cartProduct);

    }

    @Override
    public List<CartDto> cartList(String userId) {

        Member member = memberRepository.findByUserId(userId);

        Cart cart = cartRepository.findByMember(member);

        if (cart == null) {
            createCart(member);
        }

        List<CartProduct> cartProduct = cartProductRepository.findByCart(cart);

        if (cartProduct == null) {
            throw new CartProductNotFoundException("등록 된 상품 없음");
        }

        List<CartDto> dto = cartProduct.stream().map(i -> entityToDto(i)).collect(Collectors.toList());

        return dto;
    }

    @Override
    public void delete(Long id) {

        CartProduct cartProduct = cartProductRepository.findById(id)
                .orElseThrow(() -> new CartProductNotFoundException("카트에 없습니다."));

        cartProductRepository.deleteById(id);
    }

    private Cart createCart(Member member) {
        Cart cart = Cart.builder()
                .member(member)
                .build();
        return cartRepository.save(cart);
    }

}
