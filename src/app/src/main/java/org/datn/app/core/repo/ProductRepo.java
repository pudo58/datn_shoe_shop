package org.datn.app.core.repo;

import org.datn.app.core.entity.Product;
import org.datn.app.core.entity.extend.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.status = 1")
    List<Product> findAll();

    @Query("select p from Product p where p.status = 1 ORDER BY p.id DESC")
    Page<Product> findAll(Pageable pageable);

    @Query(value = "SELECT DISTINCT p.* FROM products p " +
            "INNER JOIN categories c ON c.category_id = p.category_id " +
            "INNER JOIN product_details pd ON p.product_id = pd.product_id " +
            "INNER JOIN sizes s ON s.size_id = pd.size_id " +
            "INNER JOIN colors co ON co.color_id = pd.color_id " +
            "WHERE p.status = 1  " +
            "AND (:keyword IS NULL OR MATCH(p.product_name) AGAINST (:keyword  IN BOOLEAN MODE)) " +
            "AND (p.brand_id IN (COALESCE(:brandIdList,p.brand_id))) " +
            "AND (p.category_id IN (COALESCE(:categoryIdList,p.category_id))) " +
            "AND (pd.color_id IN (COALESCE(:colorIdList,pd.color_id)))" +
            "AND (p.material IN (COALESCE(:materialList,p.material))) " +
            "AND (p.model IN (COALESCE(:modelList,p.model))) " +
            "AND (pd.size_id IN (COALESCE(:sizeIdList,pd.size_id))) " +
            "AND  c.is_trash = FALSE", nativeQuery = true)
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<Product> findByFilter(String keyword, List<Long> brandIdList, List<Long> categoryIdList, List<Long> colorIdList, List<String> materialList, List<String> modelList, List<Long> sizeIdList, Pageable pageable);
}
