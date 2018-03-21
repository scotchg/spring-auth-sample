package com.github.scotchg.sample.auth.provider.controller.web;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Controller
@SessionAttributes(value = {"callback"})
public class LoginController {

    @Data
    public static class LoginForm{
        String username;
        String password;
    }

    @Data
    public static class CallbackInfo{
        String callbackUrl;
    }

    @ModelAttribute(value = "callback")
    public CallbackInfo addCallbackInfoAttribute(){
        return new CallbackInfo();
    }

    @ModelAttribute("loginForm")
    public LoginForm addLoginAttribute() {
        return new LoginForm();
    }

    @GetMapping("login")
    private String get(@RequestParam("callbackUrl") Optional<String> callbackUrl,
                       @ModelAttribute("callback") CallbackInfo callbackInfo){
        log.info("Callback url = {}", callbackUrl.orElse("null"));
        callbackInfo.setCallbackUrl(callbackUrl.get());
        return "login";
    }

    @PostMapping("login")
    public String post(HttpServletResponse response,
                       @ModelAttribute("loginForm") LoginForm form,
                       @ModelAttribute("callback") CallbackInfo callbackInfo){
        Cookie cookie = new Cookie("token",form.getUsername());
        cookie.setMaxAge(30);
        response.addCookie(cookie);

        log.info("Login username = {}",form.getUsername());
        log.info("Login password = {}",form.getPassword());
        return "redirect:" + callbackInfo.getCallbackUrl();
    }

    @GetMapping("logout")
    public String getLogout(HttpServletRequest request, HttpServletResponse response){
        Stream.of(request.getCookies())
                .peek(cookie -> log.info("This browser has {} cookie.", cookie.getName()))
                .filter(cookie -> StringUtils.equals("token",cookie.getName()))
                .peek(cookie -> log.info("{} cookie start deletion", cookie.getName()))
                .peek(cookie -> cookie.setMaxAge(0))
                .forEach(response::addCookie);
        return "redirect:login";
    }
}
