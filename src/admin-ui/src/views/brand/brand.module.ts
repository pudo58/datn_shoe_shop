import {RouteRecordRaw} from "vue-router";
import PublisherComponent from "@/views/brand/BrandComponent.vue";
import PublisherDetailComponent from "@/views/brand/BrandDetailComponent.vue";
const BrandModule: Array<RouteRecordRaw> = [
    {
        path: '/admin/brand',
        component: PublisherComponent,
        name: 'BrandComponent'
    },
    {
        path: '/admin/brand/new',
        component: PublisherDetailComponent,
        name: 'BrandAddComponent'
    },
    {
        path: '/admin/brand/:id',
        component: PublisherDetailComponent,
        name: 'BrandEditComponent'
    }
]
export default BrandModule