import {RouteRecordRaw} from "vue-router";
import OrderComponent from "@/views/order/OrderComponent.vue";
import OrderDetailComponent from "@/views/order/OrderDetailComponent.vue";

const OrderModule: Array<RouteRecordRaw> = [
    {
        path: '/admin/order',
        component: OrderComponent,
        name: 'OrderComponent',
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/admin/order/new',
        component: OrderDetailComponent,
        name: 'OrderAddComponent',
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/admin/order/:id',
        component: OrderDetailComponent,
        name: 'OrderEditComponent',
        meta: {
            requiresAuth: true
        }
    }
]
export default OrderModule;