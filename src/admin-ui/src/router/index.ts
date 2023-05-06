import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import CategoryModule from "@/views/category/category.module";
import UserModule from "@/views/user/user.module";
import PublisherModule from "@/views/brand/brand.module";
import AttributeModule from "@/views/attribute/attribute.module";
import ProductModule from "@/views/product/product.module";
import OrderModule from "@/views/order/order.module";
const routes: Array<RouteRecordRaw> = [
    ...UserModule,
    ...PublisherModule,
    ...AttributeModule,
    ...ProductModule,
    ...CategoryModule,
    ...OrderModule,
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
