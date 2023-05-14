import {RouteRecordRaw} from "vue-router";
import CategoryDetailComponent from "@/views/category/CategoryDetailComponent.vue";
import CategoryComponent from "@/views/category/CategoryComponent.vue";
const CategoryModule: Array<RouteRecordRaw> = [
    {
        path: '/admin/category',
        component: CategoryComponent,
        name: 'CategoryComponent',
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/admin/category/new',
        component: CategoryDetailComponent,
        name: 'CategoryAddComponent',
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/admin/category/:id',
        component: CategoryDetailComponent,
        name: 'CategoryEditComponent',
        meta: {
            requiresAuth: true
        }
    }
]
export default CategoryModule