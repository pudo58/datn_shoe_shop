package org.datn.app.core.repo;

import org.datn.app.core.entity.VoucherType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherTypeRepo extends JpaRepository<VoucherType,Long> {
}
