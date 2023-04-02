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
    private final ProductDetailService productDetailService;
    private final AttributeService attributeService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product,@RequestParam("file") MultipartFile file) throws IOException {
        List<String> errorList = new ArrayList<>();
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            errorList.add("Tên sản phẩm không được để trống");
        }
        if (product.getPrice() == null || product.getPrice() < 0) {
            errorList.add("Giá sản phẩm không được để trống");
        }
        if (product.getCategory().getId() == null) {
            errorList.add("Danh mục sản phẩm không được để trống");
        }
        if (product.getPublisher().getId() == null) {
            errorList.add("Hãng sản xuất không được để trống");
        }
        if (errorList.size() > 0) {
            return ResponseEntity.badRequest().body(errorList);
        }
        Path filePath = Paths.get("C:\\Users\\Admin\\Desktop\\code\\datn_shoe_shop\\src\\server\\html\\image\\product", file.getOriginalFilename());
        product.setImageThumbnail(file.getOriginalFilename());
            Files.write(filePath, file.getBytes());
        return ResponseEntity.ok(productService.doInsert(product));
    }

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

    @PostMapping("/add-detail")
    public ResponseEntity<?> addProductDetail(@RequestBody ProductDTO product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }

}
