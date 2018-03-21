package com.github.scotchg.sample.auth.provider.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserRole {
    private String userName;
    private List<String> roles;
}
