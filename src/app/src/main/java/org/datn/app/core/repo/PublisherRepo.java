package org.datn.app.core.repo;

import org.datn.app.core.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepo extends JpaRepository<Publisher,Long> {
    List<Publisher> findByName(String name);
}
