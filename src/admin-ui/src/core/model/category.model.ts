export class Category {
    id?: number;
    name?: string;
    description?: string;
    isTrash?: boolean;
    created?: Date;
}
export class CategoryResponse extends Category {
    totalProduct?: number;
}