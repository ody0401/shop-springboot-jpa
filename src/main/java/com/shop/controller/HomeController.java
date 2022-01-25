package com.shop.controller;

import com.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final ProductService productService;

    @GetMapping(value = {"", "/"})
    public String home(Model model) {

        try {
            model.addAttribute("products",productService.newProduct());
        } catch (NullPointerException e) {
            log.info("error :{}", e.getMessage());
        }

        return "home";
    }

    @GetMapping("/dummies")
    @ResponseBody
    public ResponseEntity<String> dummy(){
        productService.dummies();

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
