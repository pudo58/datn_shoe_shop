package org.datn.app.core.repo;

import org.datn.app.core.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeRepo extends JpaRepository<Size,Long> {
    List<Size> findAllBySize(List<String> sizeList);
}
