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

    async getOrderByUserId(userId : number, page : number, size :number) {
        const response = await axios.get(this.url + "findAllByUserId/" + userId + "/" + page + "/" + size);
        return response.data;
    }

    async cancelOrder(id : number) {
        const response = await axios.post(this.url + "cancel/" + id);
        if (response.data?.status == 200) {
            toast.success(response.data?.message);
            return true;
        }else if(response.data.status == 500){
            toast.error(response.data?.message);
            return false;
        }
    }

    async returnOrder(id : number,note: string) {
        const response = await axios.post(this.url + "return/" + id + "/" + note);
        if (response.data?.status == 200) {
            toast.success(response.data?.message);
            return true;
        }else if(response.data.status == 500){
            toast.error(response.data?.message);
            return false;
        }
    }

    async receiveOrder(id : number) {
        const response = await axios.post(this.url + "receive/" + id);
        if (response.data?.status == 200) {
            toast.success(response.data?.message);
            return true;
        }else if(response.data.status == 500){
            toast.error(response.data?.message);
            return false;
        }
    }
}