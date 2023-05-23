import {Brand} from "@/core/model/brand.model";
import {Category} from "@/core/model/category.model";
import {SizeDto} from "@/core/model/size.model";
import {ProductDetail} from "@/core/model/product-detail.model";

export class Product {
    public static GENDER_MALE = 1;
    public static GENDER_FEMALE = 0;

    id?: number;
    name?: string;
    price?: number;
    description?: string;
    discount?: number;
    status?: number;
    imageThumbnail?: string;
    model?: string;
    gender?: number;
    material?: string;
    brand?: Brand;
    category?: Category;
    createdDate?: Date;
    viewMore?: boolean;

    public static getGender(gender : number) : string{
        if(gender == Product.GENDER_FEMALE){
            return "Nữ";
        }
        return "Nam";
    }
}

export class ProductDto {
    name?: string;
    price?: number;
    discount?: number;
    imageThumbnail?: string;
    description?: string;
    categoryId?: number;
    publisherId?: number;
    sizeList?: Array<SizeDto> = [] as Array<SizeDto>;
    attributeValues?: Map<number, string> = new Map<number, string>();
}

export class ProductSearchRequest {
    brandIdList?: number[];
    categoryIdList?: number[];
    gender?: number;
    keyword?: string;
    colorIdList?: number[];
    attributeIdList?: number[];
    sizeIdList?: number[];
    modelList?: string[];
    materialList?: string[];
    page?: number;
    size?: number;
}

export class ProductResponse extends Product {
    productDetailList?: ProductDetail[];
}