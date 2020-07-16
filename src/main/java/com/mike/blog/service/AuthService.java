package com.mike.blog.service;

import com.mike.blog.dto.LoginDto;
import com.mike.blog.dto.RegisterDto;
import com.mike.blog.exception.UserAlreadyExistException;
import com.mike.blog.model.Customer;
import com.mike.blog.repository.CustomerRepository;
import com.mike.blog.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public void register(RegisterDto registerDto) {
        boolean userAlreadyExist = customerRepository
                .findByUsernameOrEmail(registerDto.getUsername(), registerDto.getEmail()).isPresent();
        if (userAlreadyExist) {
            throw new UserAlreadyExistException("Sorry, but user with this username or email already exist.");
        }
        Customer customer = new Customer().builder()
                .username(registerDto.getUsername())
                .password(encodePassword(registerDto.getPassword()))
                .email(registerDto.getEmail())
                .build();
        customerRepository.save(customer);
    }

    public String login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return jwtProvider.generateToken(authenticate);
    }

    public Optional<User> getCurrentUser() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
