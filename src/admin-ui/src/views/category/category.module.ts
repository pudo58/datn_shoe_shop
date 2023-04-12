import {RouteRecordRaw} from "vue-router";
import CategoryDetailComponent from "@/views/category/CategoryDetailComponent.vue";
import CategoryComponent from "@/views/category/CategoryComponent.vue";
const CategoryModule: Array<RouteRecordRaw> = [
    {
        path: '/admin/category',
        component: CategoryComponent,
        name: 'CategoryComponent'
    },
    {
        path: '/admin/category/new',
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