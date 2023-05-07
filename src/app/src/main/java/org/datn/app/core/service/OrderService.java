package org.datn.app.core.service;

import org.datn.app.core.dto.OrderRequest;
import org.datn.app.core.entity.Cart;
import org.datn.app.core.entity.Order;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface OrderService extends ServiceCore<Order,Long> {
    Order findByCode(String code);
    Order findByCodeAndStatus(String code, String status);
    Map<String,Object> doOrder(OrderRequest model);
    ResponseEntity<?> approveOrder(Long id);
    ResponseEntity<?> cancelOrder(Long id);
    // hoàn hàng
    ResponseEntity<?> returnOrder(Long id);

    ResponseEntity<?> test(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException;
}
