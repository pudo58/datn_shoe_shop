import {User} from "@/core/model/user.model";

export class Order {
    public static PENDING_BANK_TRANSFER = -1; // chờ xác nhận chuyển khoản
    public static PENDING = 0;    // chờ xác nhận
    public static CONFIRMED = 1;  // đã xác nhận
    public static CANCELLED = 2;  // đã hủy
    public static DELIVERED = 3;  // đã giao hàng
    public static RETURNED = 4;   // trả hàng
    public static EXCHANGED = 5;  // đổi hàng
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
}

export class OrderRequest {
    cartIdList?: number[];
    userId?: number;
    address?: string;
    phoneNumber?: string;
    voucherId?: number;
    paymentMethod?: string;
}
