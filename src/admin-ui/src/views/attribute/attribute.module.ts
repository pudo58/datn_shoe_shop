import {RouteRecordRaw} from "vue-router";
import AttributeComponent from "@/views/attribute/AttributeComponent.vue";
import AttributeDetailComponent from "@/views/attribute/AttributeDetailComponent.vue";
const AttributeModule: Array<RouteRecordRaw> = [
    {
        path: '/admin/attribute',
        component: AttributeComponent,
        name: 'AttributeComponent',
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/admin/attribute/new',
        component: AttributeDetailComponent,
        name: 'AttributeDetailComponent',
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/admin/attribute/:id',
        component: AttributeDetailComponent,
        name: 'AttributeDetailComponent',
        meta: {
            requiresAuth: true
        }
    }
]
export default AttributeModule