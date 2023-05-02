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

    List<Product> findByBrandId(Long id);

    @Query("select p from Product p where p.status = 1 ORDER BY p.id DESC")
    Page<Product> findAll(Pageable pageable);

    @Query(value = "SELECT DISTINCT p.* FROM products p " +
            "INNER JOIN categories c ON c.category_id = p.category_id " +
            "INNER JOIN product_details pd ON p.product_id = pd.product_id " +
            "INNER JOIN sizes s ON s.size_id = pd.size_id " +
            "INNER JOIN colors co ON co.color_id = pd.color_id " +
            "WHERE p.status = 1  " +
            "AND (:keyword IS NULL OR MATCH(p.product_name) AGAINST (:keyword  IN BOOLEAN MODE)) " +
            "AND (COALESCE(:brandIdList) IS NULL OR p.brand_id IN (:brandIdList)) " +
            "AND (COALESCE(:categoryIdList) IS NULL OR p.category_id IN (:categoryIdList)) " +
            "AND (COALESCE(:colorIdList) IS NULL OR pd.color_id IN(:colorIdList))" +
            "AND (COALESCE(:materialList) IS NULL OR p.material IN (:materialList)) " +
            "AND (COALESCE(:materialList) IS NULL OR p.model IN (:modelList)) " +
            "AND (COALESCE(:sizeIdList) IS NULL OR pd.size_id IN (:sizeIdList)) " +
            "AND  c.is_trash = FALSE", nativeQuery = true)
    @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Page<Product> findByFilter(String keyword, List<Long> brandIdList, List<Long> categoryIdList, List<Long> colorIdList, List<String> materialList, List<String> modelList, List<Long> sizeIdList, Pageable pageable);
}
