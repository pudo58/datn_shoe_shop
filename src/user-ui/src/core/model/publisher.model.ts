export class Publisher {
    id?: number;
    name?: string;
    website?: string;
}
export class PublisherResponse extends Publisher {
    totalProduct?: number;
}