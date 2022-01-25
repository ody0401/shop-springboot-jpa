package com.shop.controller;

import com.shop.dto.BuyOneRequestDto;
import com.shop.dto.BuySomeRequestDto;
import com.shop.entity.OrderProduct;
import com.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String order(Principal principal, Model model) {

        List<OrderProduct> productList = orderService.orderList(principal.getName());

        model.addAttribute("orders", productList);

        return "order/index";
    }

    @GetMapping("/ship")
    @ResponseBody
    public ResponseEntity<String> ship(Principal principal) {

        orderService.ship(principal.getName());

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @DeleteMapping("/cancel")
    @ResponseBody
    public ResponseEntity<String> cancel(@RequestBody Map<String, Long> id) {
        orderService.cancel(id.get("id"));

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/test")
    @ResponseBody
    public ResponseEntity<String> buyOne(@RequestBody BuyOneRequestDto dto, Principal principal) {

        String userId = principal.getName();

        dto.setUserId(userId);
        orderService.buyOne(dto);

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }


    @PostMapping("/some")
    @ResponseBody
    public ResponseEntity<String> orderProduct(@RequestBody BuySomeRequestDto dto, Principal principal) {

        String userId = principal.getName();

        dto.setUserId(userId);
        orderService.buySome(dto);

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/all")
    @ResponseBody
    public ResponseEntity<String> orderAllProduct(Principal principal) {

        String userId = principal.getName();

        orderService.buyAll(userId);

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}
