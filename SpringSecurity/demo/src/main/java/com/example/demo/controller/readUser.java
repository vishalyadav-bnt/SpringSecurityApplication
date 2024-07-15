package com.example.demo.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/demo")
public class readUser {
    @GetMapping()
    public String show()
    {
        return "Access Denaied:for readUSer";
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping("/user")
    public String userEndPoint()
    {
        return "Access Denaied:for User";
    }

    @PostAuthorize("hasRole('Admin')")
    @GetMapping("/admin")
    public String adminEndPoint()
    {
        return "Access Denaied:for Admin";
    }

}
