package org.datn.app.core.repo;

import org.datn.app.core.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    Order findByCode(String code);
    Order findByCodeAndStatus(String code, String status);
    List<Order> findByUserId(Long userId);
}
