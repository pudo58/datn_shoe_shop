package org.datn.app.core.repo;

import org.datn.app.core.entity.AttributeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeDataRepo extends JpaRepository<AttributeData,Long> {
}
