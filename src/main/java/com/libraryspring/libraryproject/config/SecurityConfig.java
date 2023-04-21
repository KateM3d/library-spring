package com.libraryspring.libraryproject.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/users/").hasRole("USER")
                        .requestMatchers("/authors/").hasRole("USER")
                        .requestMatchers("/books/").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/author/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/author/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/author/delete/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/book/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/book/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/book/delete/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder().passwordEncoder(passwordEncoder::encode);
        UserDetails user = users.username("user")
                .password("password1")
                .roles("USER")
                .build();
        UserDetails admin = users.username("admin")
                .password("password2")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

}