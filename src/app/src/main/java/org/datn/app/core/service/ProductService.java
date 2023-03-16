package org.datn.app.core.service;

import org.datn.app.core.dto.ProductResponse;
import org.datn.app.core.entity.Product;
import org.datn.app.core.service.base.ServiceCore;

import java.util.List;

public interface ProductService extends ServiceCore<Product,Long> {
    List<ProductResponse> findTop10Product();
}
