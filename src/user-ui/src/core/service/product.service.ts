import axios from "axios";
import {Product} from "@/core/model/product.model";
import {toast} from "vue3-toastify";

export class ProductService {
    url = "/api/product/";
    async findAll(page: number, size: number) {
        const response = await axios.get(this.url +"page/" + page + "/" + size);
        return response.data;
    }

    async findById(id: number) {
        const response = await axios.get(this.url + id);
        return response.data;
    }

    async save(product: Product) {
        const response = await axios.post(this.url + "addDetail", product);
        try {
            if (response.data !== null  && response.data.status == 200) {
                toast.success("Thêm sản phẩm thành công");
                return response.data;
            }
        } catch (e) {
            toast.error("Thêm sản phẩm thất bại");
        }
    }

    async update(product: Product) {
        const response = await axios.put(this.url + "update", product);
        try {
            if (response.data !== null) {
                toast.success("Cập nhật sản phẩm thành công");
                return response.data;
            }
        } catch (e) {
            toast.error("Cập nhật sản phẩm thất bại");
        }
    }

    async delete(id: number) {
        const response = await axios.delete(this.url + "detele" + id);
        try {
            if (response.data !== null) {
                toast.success("Xóa sản phẩm thành công");
                return response.data;
            }
        } catch (e) {
            toast.error("Xóa sản phẩm thất bại");
        }
    }

}
