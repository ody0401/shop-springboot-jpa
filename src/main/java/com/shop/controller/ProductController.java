package com.shop.controller;

import com.shop.constant.Category;
import com.shop.dto.ProductRegisterRequestDto;
import com.shop.entity.CategoryLabel;
import com.shop.entity.Product;
import com.shop.prop.ShopProperties;
import com.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ShopProperties shopProperties;
    private final ProductService productService;

    @GetMapping("/detail/{id}")
    public String productDetail(@PathVariable("id") Long id, Model model) {

        try {
            model.addAttribute("product", productService.findProduct(id));
        } catch (NotFoundException e) {
            model.addAttribute("error",e.getMessage());
        }
        return "product/detail";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute("categoryList", categoryLabelList());
        model.addAttribute("register", new ProductRegisterRequestDto());

        return "product/register";
    }

    private List<CategoryLabel> categoryLabelList() {
        List<CategoryLabel> category = new ArrayList<>();
        category.add(new CategoryLabel( Category.TOP, "TOP"));
        category.add(new CategoryLabel(Category.OUTER, "OUTER"));
        category.add(new CategoryLabel( Category.BOTTOM, "BOTTOM"));
        category.add(new CategoryLabel(Category.SHIRTS, "SHIRTS"));
        category.add(new CategoryLabel( Category.SHOES, "SHOES"));

        return category;

    }

    @PostMapping("/register")
    public String register(@ModelAttribute("register") @Validated ProductRegisterRequestDto dto, BindingResult result,
                           Model model, RedirectAttributes rttr) throws Exception {

        if (result.hasErrors()) {

            model.addAttribute("categoryList", categoryLabelList());
            return "product/register";
        }

        MultipartFile pictureFile = dto.getPicture();


        String createdPictureFilename = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());

        dto.setPictureUrl(createdPictureFilename);


        productService.register(dto);

        rttr.addFlashAttribute("register", "상품 등록이 완료되었습니다.");

        return "redirect:/";
    }

    private String uploadFile(String originalName, byte[] fileData) throws Exception {
        UUID uid = UUID.randomUUID();

        String createFileName = uid.toString() + "_" + originalName;

        String uploadPath = shopProperties.getUploadPath();
        File target = new File(uploadPath, createFileName);

        FileCopyUtils.copy(fileData, target);

        return createFileName;

    }
}
