package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jwt.JwtUtils;
import com.example.demo.jwt.LogingRequest;
import com.example.demo.jwt.LogingResponse;
@RestController
@RequestMapping("/api")
public class readUser {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired 
    AuthenticationManager authenticationManager;
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

    @PostMapping("/e")
    public ResponseEntity<?> authenticateUser(@RequestBody LogingRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassWord()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LogingResponse response = new LogingResponse(userDetails.getUsername(), jwtToken,roles);

        return ResponseEntity.ok(response);
    }

}
