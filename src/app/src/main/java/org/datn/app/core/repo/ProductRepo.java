package org.datn.app.core.repo;

import org.datn.app.core.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    @Query("select p from Product p where p.status = 1")
    List<Product> findAll();

    @Query("select p from Product p where p.status = 1 ORDER BY p.id DESC")
    Page<Product> findAll(Pageable pageable);
}
