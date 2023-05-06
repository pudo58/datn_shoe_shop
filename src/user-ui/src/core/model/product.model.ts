import {Brand} from "@/core/model/brand.model";
import {Category} from "@/core/model/category.model";
import {Size, SizeDto} from "@/core/model/size.model";
import {ProductDetail} from "@/core/model/product-detail.model";
import {Color} from "@/core/model/color.model";

export class Product {
    id?: number;
    name?: string;
    price?: number;
    description?: string;
    discount?: number;
    status?: number;
    imageThumbnail?: string;
    model?: string;
    material?: string;
    brand?: Brand;
    category?: Category;
    createdDate?: Date;
    viewMore?: boolean;
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

export class ProductSearchRequest{
    brandIdList? : number[];
    categoryIdList? : number[];
    keyword? : string;
    colorIdList? : number[];
    attributeIdList? : number[];
    sizeIdList? : number[];
    modelList? : string[];
    materialList? : string[];
    page? : number;
    size? : number;
}

export class ProductResponse extends Product{
    productDetailList? : ProductDetail[];
}