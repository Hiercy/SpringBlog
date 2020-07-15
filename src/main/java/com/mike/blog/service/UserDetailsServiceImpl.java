package com.mike.blog.service;

import com.mike.blog.model.Customer;
import com.mike.blog.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Autowired
    public UserDetailsServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with username " + username));

        return new User(
                customer.getUsername(),
                customer.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities("ROLE_USER")
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String roleUser) {
        return Collections.singletonList(new SimpleGrantedAuthority(roleUser));
    }
}
