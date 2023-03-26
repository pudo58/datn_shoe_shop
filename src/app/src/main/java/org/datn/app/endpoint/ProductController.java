package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.ProductResponse;
import org.datn.app.core.entity.Attribute;
import org.datn.app.core.entity.Product;
import org.datn.app.core.service.AttributeService;
import org.datn.app.core.service.ProductDetailService;
import org.datn.app.core.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    private final ProductDetailService productDetailService;
    private final AttributeService attributeService;

    @GetMapping("/top10")
    public List<ProductResponse> findTop10Product(){
        return productService.findTop10Product();
    }
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product){
        List<String> errorList = new ArrayList<>();
        if(product.getName() == null || product.getName().trim().isEmpty()){
            errorList.add("Tên sản phẩm không được để trống");
        }
        if(product.getPrice() == null || product.getPrice() < 0){
            errorList.add("Giá sản phẩm không được để trống");
        }
        if(product.getCategory().getId() == null){
            errorList.add("Danh mục sản phẩm không được để trống");
        }
        if(product.getPublisher().getId() == null){
            errorList.add("Hãng sản xuất không được để trống");
        }
        if(errorList.size() > 0){
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(productService.doInsert(product));
    }
    @GetMapping("/get/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.findById(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Long id){
        productService.doDeleteById(id);
    }
    @PutMapping("/update/{id}")
    public Product updateProductById(@PathVariable Long id,@RequestBody Product product){
        return productService.doUpdateById(product,id);
    }
    @GetMapping("/page/{page}/{size}")
    public Page<Product> getAllProduct(@PathVariable int page, @PathVariable int size){
        return productService.findAll(page,size);
    }

}
