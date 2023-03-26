package org.datn.app.core.service;

import org.datn.app.core.entity.Publisher;
import org.datn.app.core.service.base.ServiceCore;

import java.util.List;

public interface PublisherService extends ServiceCore<Publisher,Long> {
    List<Publisher> findByName(String name);
}
