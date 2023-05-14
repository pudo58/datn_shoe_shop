import {User} from "@/core/model/user.model";

export class Order {
    public static PENDING_BANK_TRANSFER = -1; // chờ xác nhận chuyển khoản
    public static PENDING = 0;    // chờ xác nhận
    public static CONFIRMED = 1;  // đã xác nhận
    public static CANCELLED = 2;  // đã hủy
    public static DELIVERED = 3;  // đã giao hàng
    public static RETURNED = 4;   // trả hàng
    public static EXCHANGED = 5;  // đổi hàng
    public static RECEIVED = 6;   // đã nhận hàng
    public static DELIVERING = 7; // đang giao hàng
    id?: number;
    code?: string;
    address?: string;
    phoneNumber?: string;
    created?: Date;
    modified?: Date;
    paymentMethod?: string;
    note?: string;
    user?: User;
    status?: number;
    name?: string;
    email?: string;

    static getStatusLabel(status: number) {
        switch (status) {
            case Order.PENDING_BANK_TRANSFER:
                return 'Chờ xác nhận chuyển khoản';
            case Order.PENDING:
                return 'Chờ xác nhận';
            case Order.CONFIRMED:
                return 'Đã xác nhận';
            case Order.CANCELLED:
                return 'Đã hủy';
            case Order.DELIVERED:
                return 'Đã giao hàng';
            case Order.RETURNED:
                return 'Trả hàng';
            case Order.EXCHANGED:
                return 'Đổi hàng';
            case Order.RECEIVED:
                return 'Đã nhận hàng';
            case Order.DELIVERING:
                return 'Đang giao hàng';
            default:
                return '';
        }
    }
}

export class OrderRequest {
    cartIdList?: number[];
    userId?: number;
    address?: string;
    phoneNumber?: string;
    voucherId?: number;
    paymentMethod?: string;
    name?: string;
    email?: string;
}

export class PaymentMethod {
    public static COD = "Tiền mặt";
    // chuyển khoản ngân hàng
    public static BANK_TRANSFER = "Thẻ ATM/Internet Banking";
}
