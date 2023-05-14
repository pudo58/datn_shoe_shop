import {RouteRecordRaw} from "vue-router";
import UserDetailComponent from "@/views/user/UserDetailComponent.vue";
import UserComponent from "@/views/user/UserComponent.vue";

const UserModule: Array<RouteRecordRaw> = [
    {
        path: '/admin/user',
        component: UserComponent,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/admin/user/new',
        component: UserDetailComponent,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/admin/user/:id',
        component: UserDetailComponent,
        meta: {
            requiresAuth: true
        }
    }
]
export default UserModule;