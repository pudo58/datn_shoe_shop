package org.datn.app.core.service;

import org.datn.app.core.entity.Category;
import org.datn.app.core.service.base.ServiceCore;

public interface CategoryService extends ServiceCore<Category,Long> {
    Category findByName(String name);
}
