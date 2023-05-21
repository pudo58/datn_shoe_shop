import axios from "axios";
import {Voucher} from "@/core/model/voucher.model";
import {toast} from "vue3-toastify";
export class VoucherService{
    url = "/api/voucher/";

    async findAll(page: number, size: number) {
        const response = await axios.get(this.url + "page/" + page + "/" + size);
        return response.data;
    }

    async findById(id: number) {
        const response = await axios.get(this.url + "get/"+ id);
        return response.data;
    }

    async save(voucher: Voucher) {
        const response = await axios.post(this.url + "add", voucher);
        try {
            if (response.data !== null && response.data.status == 200) {
                toast.success(response.data.message);
                return response.data;
            }else if(response?.data?.status == 500){
                toast.error(response.data.message);
                return response.data;
            }
        } catch (e) {
            return e;
        }
    }

    async findAllVoucher() :Promise<Voucher[]>{
        const response = await axios.get<Voucher[]>(this.url + "all");
        return response.data;
    }

    async delete(id: number) {
        const response = await axios.delete(this.url + "delete/"+ id);
        try {
            if (response.data !== null) {
                toast.success("Xóa thành công");
                return response.data;
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
        const response = await axios.get(this.url + "findByCode/"+ code);
        return response.data;
    }
}