import {RouteRecordRaw} from "vue-router";
import UserDetailComponent from "@/views/user/UserDetailComponent.vue";
import UserComponent from "@/views/user/UserComponent.vue";

const UserModule: Array<RouteRecordRaw> = [
    {
        path: '/admin/user',
        component: UserComponent,
    },
    {
        path: '/admin/user/new',
        component: UserDetailComponent,
    },
    {
        path: '/admin/user/:id',
        component: UserDetailComponent,
    }
]
export default UserModule;