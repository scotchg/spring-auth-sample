package com.github.scotchg.sample.auth.provider.controller.api;

import com.github.scotchg.sample.auth.provider.domain.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@RestController
@Slf4j
public class AuthController {

    @GetMapping("/api/userRole/{token}")
    @ResponseBody
    public UserRole get(@PathVariable String token){
        log.info("Call get userRole. token = {}", token);
        switch (token){
            case "ADMIN":
                return new UserRole(token, Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
            case "USER":
                return new UserRole(token, Collections.singletonList("ROLE_USER"));
            default:
                return new UserRole(token, new ArrayList<>());
        }
    }

}
