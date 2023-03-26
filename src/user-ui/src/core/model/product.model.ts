import {Publisher} from "@/core/model/publisher.model";
import {Category} from "@/core/model/category.model";

export class Product {
    id?: number;
    name?: string;
    price?: number;
    description?: string;
    discount?: number;
    status?: number;
    publisher?: Publisher;
    category?: Category;
}