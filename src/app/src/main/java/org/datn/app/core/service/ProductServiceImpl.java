package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.ProductResponse;
import org.datn.app.core.entity.Product;
import org.datn.app.core.repo.ProductRepo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepo productRepo;
    @Override
    public Product doInsert(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product doUpdateById(Product product, Long aLong) {
        Product product1 = productRepo.findById(aLong).get();
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        product1.setDiscount(product.getDiscount());
        product1.setName(product.getName());
        return productRepo.save(product1);
    }

    @Override
    public Product doDeleteById(Long aLong) {
        return null;
    }

    @Override
    public Product findById(Long aLong) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Page<Product> findAll(Integer page, Integer size) {
        return null;
    }

    @Override
    public List<ProductResponse> findTop10Product() {
        return productRepo.findTop10Product();
    }
}
