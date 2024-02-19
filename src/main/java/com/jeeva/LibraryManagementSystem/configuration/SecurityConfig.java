package com.jeeva.LibraryManagementSystem.configuration;

import com.jeeva.LibraryManagementSystem.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Value("${student.authority}")
    private String studentAuthority;

    @Value("${admin.Authority}")
    private String adminAuthority;

    @Autowired
    private StudentService studentService;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(studentService);
        authenticationProvider.setPasswordEncoder(getPSEncode());
        return authenticationProvider;
    }

    @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorise -> authorise
                .requestMatchers("/txn/create/**").hasAnyAuthority(studentAuthority, adminAuthority)
                .requestMatchers("/txn/return/**").hasAuthority(adminAuthority)
                .anyRequest().permitAll()
                ).formLogin(withDefaults()).httpBasic(withDefaults()).csrf(csrf ->csrf.disable());
        return http.build();
    }

    @Bean
    public PasswordEncoder getPSEncode(){
        return NoOpPasswordEncoder.getInstance();
    }
}
