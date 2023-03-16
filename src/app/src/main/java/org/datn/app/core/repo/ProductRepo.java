package org.datn.app.core.repo;

import org.datn.app.core.dto.ProductResponse;
import org.datn.app.core.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    @Query(value = "SELECT p.id,p.name,p.price FROM Product p ORDER BY 1 DESC LIMIT 10", nativeQuery = true)
    List<ProductResponse> findTop10Product();
}
