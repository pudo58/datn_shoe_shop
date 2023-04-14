package org.datn.app.core.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProductDTO {
    private String name;
    private Double price;
    private Double discount;
    private String imageThumbnail;
    private String description;
    private Long categoryId;
    private Long publisherId;
    private List<SizeDTO> sizeList;
    private Map<String, String> attributeValues;
}

