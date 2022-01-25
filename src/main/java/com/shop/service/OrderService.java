package com.shop.service;

import com.shop.constant.OrderStatus;
import com.shop.dto.BuyOneRequestDto;
import com.shop.dto.BuySomeRequestDto;
import com.shop.entity.CartProduct;
import com.shop.entity.Order;
import com.shop.entity.OrderProduct;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    List<OrderProduct> orderList(String userId);

    void buySome(BuySomeRequestDto dto);

    void buyAll(String id);

    void ship(String userId);

    void cancel(Long id);

    void buyOne(BuyOneRequestDto dto);

    default OrderProduct cartToOrder(CartProduct cart, Order order) {
        return OrderProduct.builder()
                .product(cart.getProduct())
                .order(order)
                .orderStatus(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .orderPrice(cart.getProduct().getPrice() * cart.getCount())
                .count(cart.getCount())
                .build();
    }
}
