package com.shop.controller;

import com.shop.dto.SignupRequestDto;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @GetMapping("/signup")
    public String signupForm(@ModelAttribute("member") SignupRequestDto dto) {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("member") @Validated SignupRequestDto dto, BindingResult result,
                         Model model, RedirectAttributes rttr) throws Exception {
        if (result.hasErrors()) {
            return "member/signup";
        }

        if (!dto.getUserPw().equals(dto.getUserPwCheck())) {
            model.addAttribute("notEqual", "비밀번호가 일치하지 않습니다.");
            return "member/signup";
        }

        try {
            memberService.signup(dto);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/signup";
        }

        rttr.addFlashAttribute("success", "회원가입에 성공하였습니다.");

        return "redirect:/";
    }
}
