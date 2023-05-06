import axios from "axios";
import {toast} from "vue3-toastify";
import {Pageable} from "@/core/model/core.base";
import {CardRequest, Cart} from "@/core/model/cart.model";
export class CartService {
    url = "/api/cart/";

    async addToCart(cartRequest: CardRequest) {
        const response = await axios.post(this.url + "addToCart", cartRequest);
        if (response.data.status === 200) {
            toast.success(response.data?.message);
        } else {
            toast.error(response.data?.message);
        }
    }

    async getCartByUserId(cartRequest: CardRequest): Promise<Pageable<Cart>> {
        const response = await axios.post(this.url + "getCartByUserId", cartRequest);
        return response.data;
    }

    async deleteById(id: number): Promise<void> {
        const response = await axios.delete(this.url + "delete/" + id);
        if (response.data !== null) {
            toast.success("Xóa thành công");
        } else {
            toast.error("Có lỗi xảy ra, vui lòng thử lại sau");
        }
    }
}
