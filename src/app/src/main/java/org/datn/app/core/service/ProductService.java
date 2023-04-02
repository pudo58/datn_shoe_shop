package org.datn.app.core.service;

import org.datn.app.core.dto.ProductDTO;
import org.datn.app.core.entity.Product;
import org.datn.app.core.entity.extend.ProductResponse;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService extends ServiceCore<Product,Long> {
    Page<ProductResponse> findAllProduct(Integer page, Integer size);
    ResponseEntity<?> addProduct(ProductDTO productDTO);
}
