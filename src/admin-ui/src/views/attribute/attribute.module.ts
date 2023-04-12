import {RouteRecordRaw} from "vue-router";
import AttributeComponent from "@/views/attribute/AttributeComponent.vue";
import AttributeDetailComponent from "@/views/attribute/AttributeDetailComponent.vue";
const AttributeModule: Array<RouteRecordRaw> = [
    {
        path: '/admin/attribute',
        component: AttributeComponent,
        name: 'AttributeComponent'
    },
    {
        path: '/admin/attribute/new',
        component: AttributeDetailComponent,
        name: 'AttributeDetailComponent'
    },
    {
        path: '/admin/attribute/:id',
        component: AttributeDetailComponent,
        name: 'AttributeDetailComponent'
    }
]
export default AttributeModule