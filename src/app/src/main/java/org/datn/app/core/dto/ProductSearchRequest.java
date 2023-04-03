package org.datn.app.core.dto;

import lombok.Data;
import org.datn.app.core.entity.Attribute;

import java.util.List;

@Data
public class ProductSearchRequest {
    private List<Long> attributeIdList;
    private Long publisherId;
    private String name;
    private Long categoryId;

}
