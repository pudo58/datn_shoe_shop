import {OrderRequest} from "@/core/model/order.model";
import axios from "axios";
import {toast} from "vue3-toastify";

export class OrderService {
    url = "/api/order/";

    async doOrder(model: OrderRequest) {
        const response = await axios.post(this.url + "doOrder", model);
        if (response.data?.status == 200) {
            toast.success(response.data?.message);
        }else if(response.data.status == 500){
            toast.error(response.data?.message);
        }
    }

    async getPrice(model: OrderRequest) {
        const response = await axios.post(  "/api/cart/getPriceByCartIdList", model);
        return response.data;
    }
}