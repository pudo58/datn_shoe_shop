import {RouteRecordRaw} from "vue-router";
import ProductComponent from "@/views/product/ProductComponent.vue";
import ProductDetailComponent from "@/views/product/ProductDetailComponent.vue";
const ProductModule: Array<RouteRecordRaw> = [
    {
        path: '/admin/product',
        component: ProductComponent,
        name: 'ProductComponent'
    },
    {
        path: '/admin/product/new',
        component: ProductDetailComponent,
        name: 'ProductDetailComponent'
    },
    {
        path: '/admin/product/:id',
        component: ProductDetailComponent,
        name: 'ProductDetailComponent'
    }
]
export default ProductModule