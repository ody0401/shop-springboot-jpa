package com.shop.controller;

import com.shop.dto.PageRequestDto;
import com.shop.dto.PageResultDto;
import com.shop.dto.ProductDto;
import com.shop.entity.Product;
import com.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/category")
public class CategoryController {

    private final ProductService productService;

    @GetMapping("/{category}")
    public String category(PageRequestDto dto, Model model) {

        try {
            PageResultDto<ProductDto, Product> dtoList = productService.getProductList(dto);
            model.addAttribute("products", dtoList);
            model.addAttribute("totalProducts", dtoList.getDtoList().size());
        } catch (NotFoundException e) {
            model.addAttribute("productError", e.getMessage());
            model.addAttribute("totalProducts", 0);
        }

        return "category/" + dto.getCategory();
    }

    @GetMapping("/search")
    public String searchProduct() {
        return "category/search";
    }

    @PostMapping("/search")
    public String search(@RequestParam String keyword, Model model) {

        model.addAttribute("products",productService.search(keyword));
        model.addAttribute("size",productService.search(keyword).size());

        return "category/search";
    }


}