import ProductDetail from "@/core/model/product-detail.model";
import {Attribute} from "@/core/model/attribute.model";

export class AttributeData {
    id?: number;
    type?: string;
    value?: string;
    productDetail?: ProductDetail;
    attribute ?: Attribute;
}