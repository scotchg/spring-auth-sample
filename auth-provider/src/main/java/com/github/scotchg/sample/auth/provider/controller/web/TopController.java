package com.github.scotchg.sample.auth.provider.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TopController {

    @GetMapping
    public String get(RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("callbackUrl","https://www.google.co.jp/");
        return "redirect:login";
    }
}
