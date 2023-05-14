import {RouteRecordRaw} from "vue-router";
import ProductComponent from "@/views/product/ProductComponent.vue";
import ProductDetailComponent from "@/views/product/ProductDetailComponent.vue";
const ProductModule: Array<RouteRecordRaw> = [
    {
        path: '/admin/product',
        component: ProductComponent,
        name: 'ProductComponent',
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/admin/product/new',
        component: ProductDetailComponent,
        name: 'ProductDetailAddComponent',
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/admin/product/:id',
        component: ProductDetailComponent,
        name: 'ProductDetailComponent',
        meta: {
            requiresAuth: true
        }
    }
]
export default ProductModule