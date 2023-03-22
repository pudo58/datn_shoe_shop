import {Category} from "@/core/model/category.model";

export class Attribute{
    id?: number;
    name?: string;
    type?: string;
    isTrash?: boolean;
    created?: Date;
    category?: Category;
}