package org.datn.app.core.entity.extend;

import lombok.Data;
import org.datn.app.core.entity.Product;

@Data
public class ProductResponse extends Product {
    private String[] attributeList;
}
