package com.mike.blog.repository;

import com.mike.blog.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUsername(String username);

    Optional<Customer> findByUsernameOrEmail(String username, String email);
}
