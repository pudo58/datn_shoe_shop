import {RouteRecordRaw} from "vue-router";
import CategoryComponent from "@/views/category/CategoryComponent.vue";
import CategoryDetailComponent from "@/views/category/CategoryDetailComponent.vue";
const CategoryModule: Array<RouteRecordRaw> = [
    {
        path: 'category',
        component: () => import('@/views/category/CategoryComponent.vue'),
        children: [
            {
                path: '/',
                component: CategoryComponent
            },
            {
                path: ':id',
                component: CategoryDetailComponent
            },
        ]
    }
]
export default CategoryModule