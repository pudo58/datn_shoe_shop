package org.datn.app.core.dto;

import lombok.Data;

@Data
public class CartRequest {
    private Long productDetailId;
    private Integer quantity;
    private Long userId;
}
