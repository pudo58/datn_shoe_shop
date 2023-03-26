package org.datn.app.core.service;

import org.datn.app.core.entity.Publisher;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublisherService extends ServiceCore<Publisher,Long> {
    @Query("select p from Publisher p where p.name = ?1")
    List<Publisher> findByName(String name);
}
