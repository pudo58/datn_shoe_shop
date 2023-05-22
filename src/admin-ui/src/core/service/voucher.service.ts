import axios from "axios";
import {Voucher, VoucherLinkRequest} from "@/core/model/voucher.model";
import {toast} from "vue3-toastify";
import {Category} from "@/core/model/category.model";
import {Product} from "@/core/model/product.model";
import {User} from "@/core/model/user.model";

export class VoucherService {
    url = "/api/voucher/";

    async findAll(page: number, size: number) {
        const response = await axios.get(this.url + "page/" + page + "/" + size);
        return response.data;
    }

    async findById(id: number) {
        const response = await axios.get(this.url + "get/" + id);
        return response.data;
    }

    async save(voucher: Voucher) {
        const response = await axios.post(this.url + "add", voucher);
        try {
            if (response.data !== null && response.data.status == 200) {
                toast.success(response.data.message);
                return response.data;
            } else if (response?.data?.status == 500) {
                toast.error(response.data.message);
                return response.data;
            }
        } catch (e) {
            return e;
        }
    }

    async findAllVoucher(): Promise<Voucher[]> {
        const response = await axios.get<Voucher[]>(this.url + "all");
        return response.data;
    }

    async delete(id: number) {
        const response = await axios.delete(this.url + "delete/" + id);
        try {
            if (response.data !== null) {
                toast.success("Xóa thành công");
            }
        } catch (e) {
            toast.error("Xóa thất bại");
        }
    }

    async update(id: number, voucher: Voucher) {
        const response = await axios.put(this.url + "update/" + id, voucher);
        try {
            if (response.data !== null) {
                toast.success("Cập nhật thành công");
                return response.data;
            }
        } catch (e) {
            toast.error("Cập nhật thất bại");
        }
    }

    async findByCode(code: string) {
        const response = await axios.get(this.url + "findByCode/" + code);
        return response.data;
    }

    async findProductByVoucherId(id: number): Promise<Product[]> {
        const response = await axios.get(this.url + "findProductByVoucherId/" + id);
        return response.data;
    }

    async findCategoryByVoucherId(id: number): Promise<Category[]> {
        const response = await axios.get(this.url + "findCategoryByVoucherId/" + id);
        return response.data;
    }

    async findUserByVoucherId(id: number): Promise<User[]> {
        const response = await axios.get(this.url + "findAllByVoucherId/" + id);
        return response.data;
    }

    async addVoucherToProduct(model: VoucherLinkRequest) {
        const response = await axios.post(this.url + "addVoucherToProduct", model);
        try {
            if (response.data !== null) {
                toast.success("Gán sản phẩm thành công");
                return response.data;
            }
        } catch (e) {
            toast.error("Gán sản phẩm thất bại");
        }
    }

    async addVoucherToCategory(model: VoucherLinkRequest) {
        const response = await axios.post(this.url + "addVoucherToCategory", model);
        try {
            if (response.data !== null) {
                toast.success("Gán danh mục thành công");
                return response.data;
            }
        } catch (e) {
            toast.error("Gán danh mục thất bại");
        }
    }

    async addVoucherToUser(model: VoucherLinkRequest) {
        const response = await axios.post(this.url + "addVoucherToUser", model);
        try {
            if (response.data !== null) {
                toast.success("Gán người dùng thành công");
                return response.data;
            }
        } catch (e) {
            toast.error("Gán người dùng thất bại");
        }
    }

}