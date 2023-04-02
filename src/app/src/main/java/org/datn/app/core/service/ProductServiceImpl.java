package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.constant.AttributeConstant;
import org.datn.app.constant.ProductDetailConstant;
import org.datn.app.constant.ProductType;
import org.datn.app.core.dto.ProductDTO;
import org.datn.app.core.dto.SizeDTO;
import org.datn.app.core.entity.*;
import org.datn.app.core.entity.extend.ProductResponse;
import org.datn.app.core.repo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final AttributeRepo attributeRepo;
    private final ProductDetailRepo productDetailRepo;
    private final AttributeDataRepo attributeDataRepo;
    private final PublisherRepo publisherRepo;
    private final SizeRepo sizeRepo;

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
        productRepo.deleteById(aLong);
        return null;
    }

    @Override
    public Product findById(Long aLong) {

        return productRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Page<Product> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return productRepo.findAll(pageable);
    }

    @Override
    public Page<ProductResponse> findAllProduct(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<?> addProduct(ProductDTO productDTO) {
        // Lấy thông tin category và publisher từ ID
        Category category = categoryRepo.findById(productDTO.getCategoryId()).orElseThrow(
                () -> new RuntimeException("Danh mục không tồn tại")
        );
        Publisher publisher = publisherRepo.findById(productDTO.getPublisherId()).orElseThrow(
                () -> new RuntimeException("Hãng sản xuất không tồn tại")
        );

        // Tạo sản phẩm
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDiscount(productDTO.getDiscount());
        product.setImageThumbnail(productDTO.getImageThumbnail());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);
        product.setStatus(ProductType.EFFECT);
        product.setPublisher(publisher);
        List<Size> sizeList = sizeRepo.findAllBySize(productDTO.getSizeList().stream()
                .map(SizeDTO::getSize).collect(Collectors.toList()));
        for (int i = 0; i < sizeList.size(); i++) {
            ProductDetail productDetail = new ProductDetail();
            if (sizeList.get(i) == null) {
                Size size = new Size();
                size.setSize(productDTO.getSizeList().get(i).getSize());
                sizeRepo.save(size);
                productDetail.setSize(size);
                productDetail.setProduct(product);
                productDetail.setQuantity(productDTO.getSizeList().get(i).getQuantity());
                product.getProductDetails().add(productDetail);
                productDetail.setStatus(ProductDetailConstant.EFFECT);
                productDetailRepo.save(productDetail);
            } else {
                productDetail.setSize(sizeList.get(i));
                productDetail.setProduct(product);
                productDetail.setQuantity(productDTO.getSizeList().get(i).getQuantity());
                product.getProductDetails().add(productDetail);
                productDetail.setStatus(ProductDetailConstant.EFFECT);
                productDetailRepo.save(productDetail);
            }
        }
        // Lưu sản phẩm
        productRepo.save(product);

        // Thêm dữ liệu thuộc tính
        for (Map.Entry<Long, String> entry : productDTO.getAttributeValues().entrySet()) {
            Attribute attribute = attributeRepo.findById(entry.getKey()).orElseThrow(
                    () -> new RuntimeException("Thuộc tính không tồn tại")
            );
            switch (attribute.getType()){
                case AttributeConstant.INTEGER : {
                   if(!entry.getValue().matches("\\d+")){
                       throw new RuntimeException("Giá trị thuộc tính số nguyên hợp lệ");
                   }
                   break;
                }
                case AttributeConstant.STRING : {
                    if(entry.getValue().length() > 255){
                        throw new RuntimeException("Giá trị thuộc tính chuỗi không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.DATE: {
                    if(!entry.getValue().matches("\\d{4}-\\d{2}-\\d{2}")){
                        throw new RuntimeException("Giá trị thuộc tính ngày không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.BOOLEAN: {
                    if(!entry.getValue().matches("true|false")){
                        throw new RuntimeException("Giá trị thuộc tính boolean không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.DOUBLE: {
                    if(!entry.getValue().matches("\\d+\\.\\d+")){
                        throw new RuntimeException("Giá trị thuộc tính số thực không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.TIME: {
                    if(!entry.getValue().matches("\\d{2}:\\d{2}:\\d{2}")){
                        throw new RuntimeException("Giá trị thuộc tính giờ không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.DATETIME: {
                    if(!entry.getValue().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")){
                        throw new RuntimeException("Giá trị thuộc tính ngày giờ không hợp lệ");
                    }
                    break;
                }
            }
            AttributeData attributeData = new AttributeData();
            attributeData.setAttribute(attribute);
            attributeData.setProduct(product);
            attributeData.setValue(entry.getValue());
            attributeData.setType(attribute.getType());
            attributeDataRepo.save(attributeData);
        }

        return ResponseEntity.ok(product);
    }
}
