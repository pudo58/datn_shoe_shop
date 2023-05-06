import {RouteRecordRaw} from "vue-router";
import OrderComponent from "@/views/order/OrderComponent.vue";
import OrderDetailComponent from "@/views/order/OrderDetailComponent.vue";

const OrderModule: Array<RouteRecordRaw> = [
    {
        path: '/admin/order',
        component: OrderComponent,
        name: 'OrderComponent'
    },
    {
        path: '/admin/order/new',
        component: OrderDetailComponent,
        name: 'OrderAddComponent'
    },
    {
        path: '/admin/order/:id',
        component: OrderDetailComponent,
        name: 'OrderEditComponent'
    }
]
export default OrderModule;