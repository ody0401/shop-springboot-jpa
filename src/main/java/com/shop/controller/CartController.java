package com.shop.controller;

import com.shop.dto.CartAddRequestDto;
import com.shop.dto.CartDeleteDto;
import com.shop.dto.CartDto;
import com.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String cart(Principal principal, Model model) {

        String userId = principal.getName();
        List<CartDto> dto = cartService.cartList(userId);

        model.addAttribute("carts", dto);


        return "cart/index";
    }

    @PostMapping("/addCart")
    @ResponseBody
    public ResponseEntity<String> addCart(@RequestBody @Validated CartAddRequestDto dto, BindingResult bindingResult, Principal principal) {

        if(bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            fieldErrors.stream().forEach(i -> {
                sb.append(i.getDefaultMessage());
            });

            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String userId = principal.getName();
        dto.setUserId(userId);

        cartService.addCart(dto);

        return  new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestBody CartDeleteDto dto) {

        cartService.delete(dto.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
