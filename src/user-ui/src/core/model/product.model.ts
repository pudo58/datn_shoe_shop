import {Publisher} from "@/core/model/publisher.model";
import {Category} from "@/core/model/category.model";
import {SizeDto} from "@/core/model/size.model";

export class Product {
    id?: number;
    name?: string;
    price?: number;
    description?: string;
    discount?: number;
    status?: number;
    imageThumbnail?: string;
    publisher?: Publisher;
    category?: Category;
    createdDate?: Date;
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
    attributeIdList? : Array<number>;
    publisherId? : number;
    categoryId? : number;
    name? : string;
}