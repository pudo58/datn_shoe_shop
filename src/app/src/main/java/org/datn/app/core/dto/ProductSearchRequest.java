package org.datn.app.core.dto;

import lombok.Data;
import org.datn.app.core.entity.Attribute;

import java.util.List;

@Data
public class ProductSearchRequest {
    private String keyword;
    private Long[] brandIdList;
    private Long[] categoryIdList;
    private String[] materialList;
    private String[] colorList;
    private String[] modelList;
    private Long[] sizeIdList;
    private Integer page;
    private Integer size;
}
