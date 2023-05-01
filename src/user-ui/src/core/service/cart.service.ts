import axios from "axios";
import {toast} from "vue3-toastify";
import {Pageable} from "@/core/model/core.base";
import {CardRequest} from "@/core/model/cart.model";
export class CartService{
    url = "/api/cart/";

    async addToCart(cartRequest : CardRequest){
        const response = await axios.post(this.url + "addToCart", cartRequest);
        if (response.data.status === 200) {
            toast.success(response.data?.message);
        }else{
            toast.error(response.data?.message);
        }
    }
}