package com.dark.online.repository;

import com.dark.online.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Order, Long> {
    Optional<Order> findProductById(Long id);
}