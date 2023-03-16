package org.datn.app.core.service;

import org.datn.app.core.entity.Order;
import org.datn.app.core.service.base.ServiceCore;

public interface OrderService extends ServiceCore<Order,Long> {
    Order findByCode(String code);
    Order findByCodeAndStatus(String code, String status);
}
