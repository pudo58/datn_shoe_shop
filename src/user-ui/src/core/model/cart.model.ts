import {User} from "@/core/model/user.model";
import {ProductDetail} from "@/core/model/product-detail.model";

export class CardRequest {
    productDetailId?: number;
    quantity?: number;
    userId?: number;
}

export class CartDTO {
    userId?: number;
}

export class Cart{
    id?: number;
    productDetail?: ProductDetail;
    quantity?: number;
    user?: User;
    price?: number;
}