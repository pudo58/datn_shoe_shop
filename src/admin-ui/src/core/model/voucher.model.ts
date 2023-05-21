export class Voucher{
    public static DISCOUNT_TYPE_PERCENT = 0;
    public static DISCOUNT_TYPE_MONEY = 1;
    public static STATUS_ACTIVE = 1;
    public static STATUS_INACTIVE = 0;
    public static VOUCHER_TYPE_AUTO = 1;
    public static VOUCHER_TYPE_MANUAL = 2;

    id?: number;
    code?: string;
    name?: string;
    description?: string;
    image?: string;
    effectFrom?: Date;
    effectUntil?: Date;
    created ?: Date;
    quantity?: number;
    limitPerUser?: number;
    discountType?: number;
    discountValue?: number;
    isAutoApply?: boolean;
    status?: number;
    isTrash?: boolean;

     public static getDiscountTypeText(discountType: number){
        if(discountType == Voucher.DISCOUNT_TYPE_PERCENT){
            return "Phần trăm";
        }else if(discountType == Voucher.DISCOUNT_TYPE_MONEY){
            return "Tiền mặt";
        }
    }

    public static getStatusText(status: number){
        if(status == Voucher.STATUS_ACTIVE){
            return "Hoạt động";
        }else if(status == Voucher.STATUS_INACTIVE){
            return "Không hoạt động";
        }
    }
}
