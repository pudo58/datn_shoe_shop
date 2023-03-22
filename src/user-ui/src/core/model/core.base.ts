export class Pageable<Type>{
    public content?: Type[];
    public totalPages?: number;
    public totalElements?: number;
    public size?: number;
    public number?: number;
    public numberOfElements?: number;
    public first?: boolean;
    public empty?: boolean;
}