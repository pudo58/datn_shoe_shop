import {Brand} from "@/core/model/brand.model";
import {Category} from "@/core/model/category.model";
import {SizeDto} from "@/core/model/size.model";

export class Product {
    public static GENDER_MALE = 1;
    public static GENDER_FEMALE = 0;
    id?: number;
    name?: string;
    price?: number;
    description?: string;
    discount?: number;
    status?: number;
    material?: string;
    color?: string;
    model?: string;
    gender?: number;
    imageThumbnail?: string;
    brand?: Brand;
    category?: Category;
    createdDate?: Date;

    public static getGender(gender : number) : string{
        if(gender == Product.GENDER_FEMALE){
            return "Nữ";
        }
        return "Nam";
    }
}

export class ProductDto extends Product{
    categoryId?: number;
    brandId?: number;
    sizeList?: Array<SizeDto> = [] as Array<SizeDto>;
    attributeValues?: any = {};
}

export class ProductSearchRequest{
    attributeIdList? : Array<number>;
    brandId? : number;
    categoryId? : number;
    name? : string;
}