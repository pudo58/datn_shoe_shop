import {RouteRecordRaw} from "vue-router";
import PublisherComponent from "@/views/publisher/PublisherComponent.vue";
import PublisherDetailComponent from "@/views/publisher/PublisherDetailComponent.vue";
const PublisherModule: Array<RouteRecordRaw> = [
    {
        path: '/admin/publisher',
        component: PublisherComponent,
        name: 'PublisherComponent'
    },
    {
        path: '/admin/publisher/new',
        component: PublisherDetailComponent,
        name: 'PublisherAddComponent'
    },
    {
        path: '/admin/publisher/:id',
        component: PublisherDetailComponent,
        name: 'PublisherEditComponent'
    }
]
export default PublisherModule