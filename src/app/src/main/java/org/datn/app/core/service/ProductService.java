package org.datn.app.core.service;

import org.datn.app.core.dto.ProductDTO;
import org.datn.app.core.dto.ProductSearchRequest;
import org.datn.app.core.entity.Product;
import org.datn.app.core.entity.extend.ProductResponse;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ProductService extends ServiceCore<Product,Long> {
    Page<ProductResponse> findAllProduct(Integer page, Integer size);
    ResponseEntity<?> addProduct(ProductDTO productDTO);

    ResponseEntity<?> addImage(MultipartFile file, HttpServletRequest request) throws IOException;

    List<Product> searchByMultiCondition(ProductSearchRequest model);
}
