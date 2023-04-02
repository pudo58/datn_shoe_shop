package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.ProductDTO;
import org.datn.app.core.entity.Product;
import org.datn.app.core.service.AttributeService;
import org.datn.app.core.service.ProductDetailService;
import org.datn.app.core.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.doDeleteById(id);
    }

    @PutMapping("/update/{id}")
    public Product updateProductById(@PathVariable Long id, @RequestBody Product product) {
        return productService.doUpdateById(product, id);
    }

    @GetMapping("/page/{page}/{size}")
    public Page<Product> getAllProduct(@PathVariable int page, @PathVariable int size) {
        return productService.findAll(page, size);
    }

    @PostMapping("/addDetail")
    public ResponseEntity<?> addProductDetail(@RequestBody ProductDTO product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }

}
