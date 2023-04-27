package org.datn.app.core.entity.extend;

import lombok.Getter;
import lombok.Setter;
import org.datn.app.core.entity.Attribute;
import org.datn.app.core.entity.Product;

@Getter
@Setter
public class ProductResponse extends Product {
    private Attribute attribute;
}
