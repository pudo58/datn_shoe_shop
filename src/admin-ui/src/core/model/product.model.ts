import {Brand} from "@/core/model/brand.model";
import {Category} from "@/core/model/category.model";
import {SizeDto} from "@/core/model/size.model";

export class Product {
    id?: number;
    name?: string;
    price?: number;
    description?: string;
    discount?: number;
    status?: number;
    material?: string;
    color?: string;
    model?: string;
    imageThumbnail?: string;
    brand?: Brand;
    category?: Category;
    createdDate?: Date;
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