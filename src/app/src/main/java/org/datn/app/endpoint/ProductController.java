package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.ProductResponse;
import org.datn.app.core.entity.Attribute;
import org.datn.app.core.service.AttributeService;
import org.datn.app.core.service.ProductDetailService;
import org.datn.app.core.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void addProduct(){

    }

}
