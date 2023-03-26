import {Product} from "@/core/model/product.model";
import {Image} from "@/core/model/image.model";
import {Size} from "@/core/model/size.model";
import {AttributeData} from "@/core/model/attribute-data.model";

export default class ProductDetail {
    id?: number;
    quantity?: number;
    product?: Product;
    size?: Size;
    imageList?: Array<Image>;
    attributeValueList?: Array<AttributeData>;
}