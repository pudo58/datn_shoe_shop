export class Pageable<Type> {
    public content?: Type[];
    public totalPages?: number;
    public totalElements?: number;
    public size?: number;
    public number?: number;
    public numberOfElements?: number;
    public first?: boolean;
    public empty?: boolean;
    public last?: boolean;
}

export const PAGEABLE_DEFAULT  = {
    SIZE : 1000000,
    PAGE : 0
}

export const COLOR = [
    {id: 1, name: 'Đen'},
    {id: 2, name: 'Trắng'},
    {id: 3, name: 'Xanh'},
    {id: 4, name: 'Đỏ'},
    {id: 5, name: 'Vàng'},
    {id: 6, name: 'Cam'},
    {id: 7, name: 'Hồng'},
    {id: 8, name: 'Tím'},
    {id: 9, name: 'Xám'},
    {id: 10, name: 'Nâu'},
    {id: 11, name: 'Xanh lá'}
]