package org.datn.app.core.service;

import org.datn.app.core.dto.OrderRequest;
import org.datn.app.core.entity.Cart;
import org.datn.app.core.entity.Order;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.http.ResponseEntity;

public interface OrderService extends ServiceCore<Order,Long> {
    Order findByCode(String code);
    Order findByCodeAndStatus(String code, String status);
    ResponseEntity<?> doOrder(OrderRequest model);
    ResponseEntity<?> approveOrder(Long id);
    ResponseEntity<?> cancelOrder(Long id);
    // hoàn hàng
    ResponseEntity<?> returnOrder(Long id);
}
