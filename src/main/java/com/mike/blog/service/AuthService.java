package com.mike.blog.service;

import com.mike.blog.dto.RegisterDto;
import com.mike.blog.model.Customer;
import com.mike.blog.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;

    @Autowired
    public AuthService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void register(RegisterDto registerDto) {
        Customer customer = new Customer().builder()
                .username(registerDto.getUsername())
                .password(registerDto.getPassword())
                .email(registerDto.getEmail())
                .build();

        customerRepository.save(customer);
    }
}
