package org.datn.app.constant;

public class OrderConstant {
    public static final String ORDER_NEW = "Đơn hàng mới";
    public static final String ORDER_CONFIRMED = "Đơn hàng đã xác nhận";
    public static final String ORDER_CANCELLED = "Đơn hàng đã hủy";

    // Lý do hủy
    public static final String CANCELLED_REASON_OUT_OF_STOCK = "Hết hàng";
    public static final String CANCELLED_REASON_CUSTOMER = "Khách hàng hủy";
    public static final String CANCELLED_REASON_OTHER = "Lý do khác";

    // Trạng thái đơn hàng
    public static final Integer PENDING_BANK_TRANSFER = -1; // chờ xác nhận chuyển khoản
    public static final Integer PENDING = 0;    // chờ xác nhận
    public static final Integer CONFIRMED = 1;  // đã xác nhận
    public static final Integer CANCELLED = 2;  // đã hủy
    public static final Integer DELIVERED = 3;  // đã giao hàng
    public static final Integer RETURNED = 4;   // trả hàng
    public static final Integer EXCHANGED = 5;  // đổi hàng

}
