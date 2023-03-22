package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.constant.AttributeType;
import org.datn.app.core.entity.Attribute;
import org.datn.app.core.service.AttributeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/attribute")
@RequiredArgsConstructor
public class AttributeController {
    private final AttributeService attributeService;

    @PostMapping("/add")
    public ResponseEntity<?> saveAttribute(Attribute attribute){
        List<String> errorList = new ArrayList<>();
        if(attribute.getName().isEmpty()){
            errorList.add("Tên thuộc tính không được để trống");
        }
        if(attributeService.findByName(attribute.getName()) != null){
            errorList.add("Tên thuộc tính đã tồn tại");
        }
        if(attribute.getType().trim().isEmpty() || attribute.getType() == null ){
            errorList.add("Loại thuộc tính không được để trống");
        }
        if(!(attribute.getType().equals(AttributeType.STRING)||attribute.getType().equals(AttributeType.BOOLEAN)
                ||attribute.getType().equals(AttributeType.DATE)||attribute.getType().equals(AttributeType.DATETIME)
                ||attribute.getType().equals(AttributeType.DOUBLE)||attribute.getType().equals(AttributeType.INTEGER)
                ||attribute.getType().equals(AttributeType.TIME))){
            errorList.add("Loại thuộc tính không hợp lệ");
        }
        if(errorList.size() > 0){
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(attributeService.doInsert(attribute));
    }
    @GetMapping("/page/{page}/{size}")
    public Page<Attribute> getAllAttribute(@PathVariable int page, @PathVariable int size){
        return attributeService.findAll(page,size);
    }
    @GetMapping("/find/{name}")
    public Attribute findAttributeByName(@PathVariable String name){
        return attributeService.findByName(name);
    }
    @GetMapping("/get/{id}")
    public Attribute findAttributeById(@PathVariable Long id){
        return attributeService.findById(id);
    }
    @PutMapping("/update/{id}")
    public Attribute updateById(Long id,Attribute attribute){
        return attributeService.doUpdateById(attribute,id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        attributeService.doDeleteById(id);
    }
}
