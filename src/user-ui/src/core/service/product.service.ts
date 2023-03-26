import axios from "axios";
import {Product} from "@/core/model/product.model";
import {toast} from "vue3-toastify";

export class ProductService {
    async findAll(page: number, size: number) {
        const response = await axios.get("/api/product/page/" + page + "/" + size);
        return response.data;
    }

    async findById(id: number) {
        const response = await axios.get("/api/product/" + id);
        return response.data;
    }

    async save(product: Product) {
        const response = await axios.post("/api/product/add", product);
        try {
            if (response.data !== null) {
                toast.success("Thêm sản phẩm thành công");
                return response.data;
            }
        } catch (e) {
            toast.error("Thêm sản phẩm thất bại");
        }
    }

    async update(product: Product) {
        const response = await axios.put("/api/product/update", product);
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
        const response = await axios.delete("/api/product/delete/" + id);
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
