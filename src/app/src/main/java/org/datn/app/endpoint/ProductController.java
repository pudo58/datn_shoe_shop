package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.ProductDTO;
import org.datn.app.core.dto.ProductRequest;
import org.datn.app.core.entity.Product;
import org.datn.app.core.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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

    @PostMapping(value = "/addDetail")
    public ResponseEntity<?> addProductDetail(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @PostMapping(value = "/addImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addImage(MultipartFile file, HttpServletRequest request) throws IOException, IOException {
        return productService.addImage(file, request);
    }
}
