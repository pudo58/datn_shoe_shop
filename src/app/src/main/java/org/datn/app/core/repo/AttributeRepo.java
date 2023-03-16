package org.datn.app.core.repo;

import org.datn.app.core.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepo extends JpaRepository<Attribute,Long> {
    Attribute findByName(String name);
}
