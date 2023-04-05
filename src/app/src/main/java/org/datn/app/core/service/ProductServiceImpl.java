package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.constant.AttributeConstant;
import org.datn.app.constant.ProductDetailConstant;
import org.datn.app.constant.ProductConstant;
import org.datn.app.core.dto.ProductDTO;
import org.datn.app.core.dto.ProductSearchRequest;
import org.datn.app.core.dto.SizeDTO;
import org.datn.app.core.entity.*;
import org.datn.app.core.entity.extend.ProductResponse;
import org.datn.app.core.repo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Product product = productRepo.findById(aLong).get();
        product.setStatus(0);
        return productRepo.save(product);
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
        if (page == null) page = 0;
        if (size == null) size = 15;
        if (page < 0) page = 0;
        if (size < 0) size = 15;
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return productRepo.findAll(pageable);
    }

    @Override
    public Page<ProductResponse> findAllProduct(Integer page, Integer size) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
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
        try {
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setDiscount(productDTO.getDiscount());
            product.setDescription(productDTO.getDescription());
            product.setCategory(category);
            product.setStatus(ProductConstant.EFFECT);
            product.setPublisher(publisher);
        } catch (RuntimeException e) {
            throw new RuntimeException("có lỗi xảy ra khi thêm sản phẩm");
        }
        List<SizeDTO> sizeList = productDTO.getSizeList();
        if (sizeList.size() == 0) {
            //throw new RuntimeException("Sản phẩm phải có ít nhất 1 kích thước");
        }
        for (int i = 0; i < sizeList.size(); i++) {
            ProductDetail productDetail = new ProductDetail();
            if (sizeRepo.findBySize(sizeList.get(i).getSize()) == null) {
                try {
                    Size size = new Size();
                    size.setSize(productDTO.getSizeList().get(i).getSize());
                    sizeRepo.save(size);
                    productDetail.setSize(sizeRepo.findBySize(size.getSize()));
                    productDetail.setProduct(product);
                    Integer quantity = productDTO.getSizeList().get(i).getQuantity();
                    if (quantity < 0) {
                        throw new RuntimeException("Số lượng không hợp lệ");
                    }
                    productDetail.setQuantity(quantity);
                    product.getProductDetails().add(productDetail);
                    productDetail.setStatus(ProductDetailConstant.EFFECT);
                    productDetailRepo.save(productDetail);
                } catch (RuntimeException e) {
                    throw new RuntimeException("có lỗi xảy ra khi thêm kích thước");
                }
            } else {
                try {
                    productDetail.setSize(sizeRepo.findBySize(sizeList.get(i).getSize()));
                    productDetail.setProduct(product);
                    Integer quantity = productDTO.getSizeList().get(i).getQuantity();
                    if (quantity < 0) {
                        throw new RuntimeException("Số lượng không hợp lệ");
                    }
                    productDetail.setQuantity(quantity);
                    product.getProductDetails().add(productDetail);
                    productDetail.setStatus(ProductDetailConstant.EFFECT);
                    productDetailRepo.save(productDetail);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    throw new RuntimeException("có lỗi xảy ra khi thêm kích thước");
                }
            }
        }
        // Lưu sản phẩm
        productRepo.save(product);

        // Thêm dữ liệu thuộc tính
        for (Map.Entry<Long, String> entry : productDTO.getAttributeValues().entrySet()) {
            Attribute attribute = attributeRepo.findById(entry.getKey()).orElseThrow(
                    () -> new RuntimeException("Thuộc tính không tồn tại")
            );
            switch (attribute.getType()) { // cây validate
                case AttributeConstant.INTEGER: {
                    if (!entry.getValue().matches("\\d+")) {
                        throw new RuntimeException("Giá trị thuộc tính số nguyên hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.STRING: {
                    if (entry.getValue().length() > 255) {
                        throw new RuntimeException("Giá trị thuộc tính chuỗi không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.DATE: {
                    if (!entry.getValue().matches("\\d{2}-\\d{2}-\\d{4}")) {
                        throw new RuntimeException("Giá trị thuộc tính ngày không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.BOOLEAN: {
                    if (!entry.getValue().matches("true|false")) {
                        throw new RuntimeException("Giá trị thuộc tính boolean không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.DOUBLE: {
                    if (!entry.getValue().matches("\\d+\\.\\d+")) {
                        throw new RuntimeException("Giá trị thuộc tính số thực không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.TIME: {
                    if (!entry.getValue().matches("\\d{2}:\\d{2}:\\d{2}")) {
                        throw new RuntimeException("Giá trị thuộc tính giờ không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.DATETIME: {
                    if (!entry.getValue().matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}")) {
                        throw new RuntimeException("Giá trị thuộc tính ngày giờ không hợp lệ");
                    }
                    break;
                }
            }
            try {
                AttributeData attributeData = new AttributeData();
                attributeData.setAttribute(attribute);
                attributeData.setProduct(product);
                attributeData.setValue(entry.getValue());
                attributeData.setType(attribute.getType());
                attributeDataRepo.save(attributeData);
            } catch (RuntimeException e) {
                throw new RuntimeException("có lỗi xảy ra khi thêm thuộc tính");
            }
        }
        // Khi thêm thành công sẽ trả về sản phẩm vừa thêm và thông báo thành công
        Map<String, Object> response = new HashMap<>();
        response.put("product", product);
        response.put("message", "Thêm sản phẩm thành công");
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> addImage(MultipartFile file, HttpServletRequest request) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Product product = productRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Sản phẩm không tồn tại")
        );
        Path filePath = Paths.get("C:\\Users\\Admin\\Desktop\\code\\datn_shoe_shop\\src\\server\\html\\image\\product", file.getOriginalFilename());
        product.setImageThumbnail(file.getOriginalFilename());
        if (productRepo.save(product) != null)
            Files.write(filePath, file.getBytes());
        return ResponseEntity.ok(product);
    }

    @Override
    public List<Product> searchByMultiCondition(ProductSearchRequest model) {
        List<Product> products = productRepo.findAll();
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getName() != null) {
                if (product.getName().contains(model.getName().toLowerCase())) {
                    result.add(product);
                }
            }
            if (model.getCategoryId() != null) {
                if (product.getCategory().getId() == model.getCategoryId()) {
                    result.add(product);
                }
            }
            if (model.getPublisherId() != null) {
                if (product.getPublisher().getId() == model.getPublisherId()) {
                    result.add(product);
                }
            }
            for (int i = 0; i < model.getAttributeIdList().size(); i++) {
                if (model.getAttributeIdList().get(i) != null) {
                    if (product.getAttributeData().get(i).getAttribute().getId() == model.getAttributeIdList().get(i)) {
                        result.add(product);
                    }
                }
            }

        }
        return result;
    }
}
