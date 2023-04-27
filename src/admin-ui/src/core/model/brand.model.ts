export class Brand {
    id?: number;
    name?: string;
    website?: string;
}
export class BrandResponse extends Brand {
    totalProduct?: number;
}