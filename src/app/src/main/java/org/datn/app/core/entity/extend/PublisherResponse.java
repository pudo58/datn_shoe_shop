package org.datn.app.core.entity.extend;

import lombok.Data;
import org.datn.app.core.entity.Publisher;

@Data
public class PublisherResponse extends Publisher {
    private long totalProduct;
}
