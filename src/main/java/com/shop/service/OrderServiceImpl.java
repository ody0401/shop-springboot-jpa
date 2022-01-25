package com.shop.service;

import com.shop.constant.ItemSellStatus;
import com.shop.constant.OrderStatus;
import com.shop.dto.BuyOneRequestDto;
import com.shop.dto.BuySomeRequestDto;
import com.shop.entity.*;
import com.shop.exception.CartProductNotFoundException;
import com.shop.exception.OverCountException;
import com.shop.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    private final CartProductRepository cartProductRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Override
    public List<OrderProduct> orderList(String userId) {

        Member member = memberRepository.findByUserId(userId);

        Order order = orderRepository.findByMember(member);

        if (order != null) {
            List<OrderProduct> orderProducts = order.getOrderProducts();
            return orderProducts;
        }
       return null;
    }

    @Override
    public void buySome(BuySomeRequestDto dto) {

        Member member = memberRepository.findByUserId(dto.getUserId());

        Order existingOrder = orderRepository.findByMember(member);
        if (existingOrder == null) {
            Order order = Order.builder()
                    .member(member)
                    .build();
            Order newOrder = orderRepository.save(order);
            saveOrderProduct(dto, newOrder);
        } else {
            saveOrderProduct(dto, existingOrder);
        }
    }

    @Override
    public void buyAll(String id) {

        Member member = memberRepository.findByUserId(id);

        Cart cart = cartRepository.findByMember(member);

        List<CartProduct> carts = cartProductRepository.findByCart(cart);

        Order existingOrder = orderRepository.findByMember(member);

        if (existingOrder == null) {
            Order order = Order.builder()
                    .member(member)
                    .build();
            Order newOrder = orderRepository.save(order);
            carts.stream().map(i -> cartToOrder(i, newOrder))
                    .forEach(this::overCountCheck);
        } else {
            carts.stream().map(i -> cartToOrder(i, existingOrder))
                    .forEach(this::overCountCheck);
        }
    }

    @Override
    public void ship(String userId) {
        Member member = memberRepository.findByUserId(userId);

        Order order = orderRepository.findByMember(member);

        if (order != null) {
            order.getOrderProducts().stream().forEach(i -> {
                 i.setOrderStatus(OrderStatus.SHIP);
                 orderProductRepository.save(i);
            });
        }
    }

    @Override
    public void cancel(Long id) {
        orderProductRepository.deleteById(id);
    }

    @Override
    public void buyOne(BuyOneRequestDto dto) {

        Member member = memberRepository.findByUserId(dto.getUserId());

        Order existingOrder = orderRepository.findByMember(member);

        Product product = productRepository.findById(dto.getId()).orElseThrow(() -> {
            throw new IllegalStateException("상품을 찾지못함");
        });

        if (dto.getCount() > product.getStockNumber()) {
            throw new OverCountException("수량 초과");
        }



        if (existingOrder == null) {

            Order order = Order.builder()
                    .member(member)
                    .build();

            Order newOrder = orderRepository.save(order);

            buy(dto, newOrder, product);

        } else {

            buy(dto, existingOrder, product);

        }

    }

    private void buy(BuyOneRequestDto dto, Order existingOrder, Product product) {
        OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .order(existingOrder)
                .orderStatus(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .count(dto.getCount())
                .orderPrice(product.getPrice() * dto.getCount())
                .build();

        orderProductRepository.save(orderProduct);

        int stock = product.getStockNumber() - dto.getCount();
        if (stock == 0) {
            product.setStockNumber(stock);
            product.setProductSellStatus(ItemSellStatus.SOLD_OUT);
            productRepository.save(product);
        } else {
            product.setStockNumber(stock);
            productRepository.save(product);
        }
    }

    private void overCountCheck(OrderProduct entity){
        Product product = entity.getProduct();
        int stock = product.getStockNumber();
        int count = entity.getCount();

        if (stock >= count) {
            orderProductRepository.save(entity);
            product.setStockNumber(stock - count);
            Product updatedProduct = productRepository.save(product);
            if (updatedProduct.getStockNumber() == 0) {
                updatedProduct.setProductSellStatus(ItemSellStatus.SOLD_OUT);
                productRepository.save(updatedProduct);
            }
        } else {
            throw new OverCountException("수량 한도 초과");
        }
    }

    private void saveOrderProduct(BuySomeRequestDto dto, Order order) {
        dto.getId().stream().map(i -> Long.parseLong(i.get("id")))
                .map(id -> cartProductRepository.findById(id).orElseThrow(
                        () -> new CartProductNotFoundException("상품을 찾지 못함")))
                .map(cartProduct -> cartToOrder(cartProduct, order))
                .forEach(this::overCountCheck);
    }
}
