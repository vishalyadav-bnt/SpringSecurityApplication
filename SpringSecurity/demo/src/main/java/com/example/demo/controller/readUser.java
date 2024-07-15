package com.example.demo.controller;

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

}
