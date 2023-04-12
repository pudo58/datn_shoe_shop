import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import CategoryModule from "@/views/category/category.module";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/admin',
        component: () => import('@/views/DashboardView.vue'),
        name: 'DashboardView'
    },
    ...CategoryModule,


]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes : routes
})

export default router
