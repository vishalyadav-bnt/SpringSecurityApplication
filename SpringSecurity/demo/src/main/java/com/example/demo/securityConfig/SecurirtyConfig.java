package com.example.demo.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurirtyConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
         http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }


    @Bean
    UserDetailsService userDetailsService()
    {UserDetails user1= User.withUsername("user")
                .password("{noop}password")
                .roles("User")
                .build();
        UserDetails admin= User.withUsername("Admin")
                .password("{noop}password")
                .roles("Admin")
                .build();
        return new InMemoryUserDetailsManager(user1,admin);
    }


}
