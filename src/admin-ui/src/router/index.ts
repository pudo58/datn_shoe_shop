import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import CategoryModule from "@/views/category/category.module";
import UserModule from "@/views/user/user.module";
import PublisherModule from "@/views/brand/brand.module";
import AttributeModule from "@/views/attribute/attribute.module";
import ProductModule from "@/views/product/product.module";
import OrderModule from "@/views/order/order.module";
import VoucherModule from "@/views/voucher/voucher.module";
const routes: Array<RouteRecordRaw> = [
    ...UserModule,
    ...PublisherModule,
    ...AttributeModule,
    ...ProductModule,
    ...CategoryModule,
    ...OrderModule,
    ...VoucherModule,
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/LoginView.vue'),
    },
    {
        path: '/chart',
        name: 'Chart',
        component: () => import('../views/chart/ChartComponent.vue'),
        meta: {
            requiresAuth: true
        }
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem("access_token");
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (token === null || token === undefined || token === "") {
            next({
                path: '/login',
                query: {redirect: to.fullPath}
            })
        } else {
            next()
        }
    } else {
        next()
    }
});

export default router
