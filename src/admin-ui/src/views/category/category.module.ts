import {RouteRecordRaw} from "vue-router";
import CategoryDetailComponent from "@/views/category/CategoryDetailComponent.vue";
const CategoryModule: Array<RouteRecordRaw> = [
    {
        path: '/admin/category',
        component: () => import('@/views/category/CategoryComponent.vue'),
        name: 'CategoryComponent'
    },
    {
        path: '/admin/category/add',
        component: CategoryDetailComponent,
        name: 'CategoryAddComponent'
    },
    {
        path: '/admin/category/:id',
        component: CategoryDetailComponent,
        name: 'CategoryEditComponent'
    }
]
export default CategoryModule