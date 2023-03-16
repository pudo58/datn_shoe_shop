package org.datn.app.core.repo;

import org.datn.app.core.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
    Cart findByUserIdAndAndProductDetailId(Long userId, Long productDetailId);
}
