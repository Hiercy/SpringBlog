package com.mike.blog.service;

import com.mike.blog.dto.RegisterDto;
import com.mike.blog.model.Customer;
import com.mike.blog.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterDto registerDto) {
        Customer customer = new Customer().builder()
                .username(registerDto.getUsername())
                .password(encodePassword(registerDto.getPassword()))
                .email(registerDto.getEmail())
                .build();

        customerRepository.save(customer);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
