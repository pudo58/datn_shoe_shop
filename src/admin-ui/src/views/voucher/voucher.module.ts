import {RouteRecordRaw} from "vue-router";
import VoucherComponent from "@/views/voucher/VoucherComponent.vue";
import VoucherDetailComponent from "@/views/voucher/VoucherDetailComponent.vue";

const VoucherModule: Array<RouteRecordRaw> = [
    {
        path: '/admin/voucher',
        component: VoucherComponent,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/admin/voucher/new',
        component: VoucherDetailComponent,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/admin/voucher/:id',
        component: VoucherDetailComponent,
        meta: {
            requiresAuth: true
        }
    }
]
export default VoucherModule;