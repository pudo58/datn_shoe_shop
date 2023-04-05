package org.datn.app.core.repo;

import org.datn.app.core.entity.VoucherProductCategoryLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherProductCategoryLinkRepo extends JpaRepository<VoucherProductCategoryLink, Long> {
}
