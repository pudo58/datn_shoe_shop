import {User} from "@/core/model/user.model";

export class Order{
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

    static getStatus(status : number): string{
        switch (status) {
            case Order.PENDING:
                return "Chờ xác nhận";
            case Order.CONFIRMED:
                return "Đã xác nhận";
            case Order.CANCELLED:
                return "Đã hủy";
            case Order.DELIVERED:
                return "Đã giao hàng";
            case Order.RETURNED:
                return "Trả hàng";
            case Order.EXCHANGED:
                return "Đổi hàng";
            case Order.RECEIVED:
                return "Đã nhận hàng";
            case Order.DELIVERING:
                return "Đang giao hàng";
            default:
                return "";
        }
    }
}

export const ORDER_STATUS_LIST = [
    {value: Order.PENDING, label: Order.getStatus(Order.PENDING)},
    {value: Order.CONFIRMED, label: Order.getStatus(Order.CONFIRMED)},
    {value: Order.CANCELLED, label: Order.getStatus(Order.CANCELLED)},
    {value: Order.DELIVERED, label: Order.getStatus(Order.DELIVERED)},
    {value: Order.RETURNED, label: Order.getStatus(Order.RETURNED)},
    {value: Order.EXCHANGED, label: Order.getStatus(Order.EXCHANGED)},
    {value: Order.RECEIVED, label: Order.getStatus(Order.RECEIVED)},
    {value: Order.DELIVERING, label: Order.getStatus(Order.DELIVERING)},
];

export class OrderStatisticalDto{
    keyword?: string;
    brandId? : number;
    categoryId?: number;
    status?:number;
    fromDate?: Date;
    toDate?: Date;
    page?: number;
    size?: number;
}