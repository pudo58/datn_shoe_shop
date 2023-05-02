package org.datn.app.core.entity.extend;

import lombok.Getter;
import lombok.Setter;
import org.datn.app.core.entity.Attribute;
import org.datn.app.core.entity.Product;
import org.datn.app.core.entity.ProductDetail;

import java.util.List;

@Getter
@Setter
public class ProductResponse extends Product {
    private Attribute attribute;
    private List<ProductDetail> productDetailList;
}
