package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.Category;
import org.datn.app.core.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        List<String> errorList = new ArrayList<>();
        if(category.getName().isEmpty()){
            errorList.add("Tên danh mục không được để trống");
        }
        if(categoryService.findByName(category.getName()) != null){
            errorList.add("Tên danh mục đã tồn tại");
        }
        if(errorList.size() > 0){
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(categoryService.doInsert(category));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id,@RequestBody Category category){
        List<String> errorList = new ArrayList<>();
        if(category.getName().isEmpty()){
            errorList.add("Tên danh mục không được để trống");
        }
        if(categoryService.findByName(category.getName()) != null){
            errorList.add("Tên danh mục đã tồn tại");
        }
        if(errorList.size() > 0){
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(categoryService.doUpdateById(category,id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.doDeleteById(id));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping("/page/{page}/{size}")
    public ResponseEntity<?> getAllCategory(@PathVariable int page,@PathVariable int size){
        return ResponseEntity.ok(categoryService.findAll(page,size));
    }
    @GetMapping("/find/{name}")
    public Category findCategoryByName(@PathVariable String name){
        return categoryService.findByName(name);
    }

}