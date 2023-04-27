package org.datn.app.core.repo;

import org.datn.app.core.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepo extends JpaRepository<Brand,Long> {
    List<Brand> findByName(String name);
}
