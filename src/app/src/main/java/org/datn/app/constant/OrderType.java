package org.datn.app.constant;

public class OrderType {
    public static final Integer PENDING = 0;    // chờ xác nhận
    public static final Integer CONFIRMED = 1;  // đã xác nhận
    public static final Integer CANCELLED = 2;  // đã hủy
    public static final Integer DELIVERED = 3;  // đã giao hàng
    public static final Integer RETURNED = 4;   // trả hàng
    public static final Integer EXCHANGED = 5;  // đổi hàng
}
