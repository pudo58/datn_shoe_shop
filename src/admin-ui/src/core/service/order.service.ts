import axios from "axios";
import {toast} from "vue3-toastify";
import {Order, OrderStatisticalDto} from "@/core/model/order.model";
import {Pageable} from "@/core/model/core.base";

export class OrderService {
    url = "/api/order/";

    async findAll(page: number, size: number): Promise<Pageable<Order>> {
        const response = await axios.get(this.url + "findAll/" + page + "/" + size);
        return response.data;
    }

    async approve(id: number) {
        const response = await axios.post(this.url + "approve/" + id);
        if (response.data?.status == 200) {
            toast.success(response.data?.message);
        } else if (response.data.status == 500) {
            toast.error(response.data?.message);
        }
    }

    async findAllByStatus(model: OrderStatisticalDto) {
        const response = await axios.post(this.url + "findAllByStatus", model);
        return response.data;
    }

    async delivery(id: number) {
        const response = await axios.post(this.url + "delivery/" + id);
        if (response.data?.status == 200) {
            toast.success(response.data?.message);
        } else if (response.data.status == 500) {
            toast.error(response.data?.message);
        }
    }

    async detail(id: number) {
        const response = await axios.get(this.url + "detail/" + id);
        return response.data;
    }
}
