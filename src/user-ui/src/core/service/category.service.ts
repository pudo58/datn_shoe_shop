import axios from "axios";
import {toast} from "vue3-toastify";
import {Pageable} from "@/core/model/core.base";
import {Category} from "@/core/model/category.model";

export class CategoryService {
    async findAll(page: number, size: number): Promise<Pageable<Category>> {
        const response = await axios.get("/api/category/page/" + page + "/" + size);
        return response.data;
    }

    async save(category: Category): Promise<Category> {
        return await axios.post("/api/category/add", category).then((response) => {
            if (response.data !== null) {
                toast.success("Thêm mới thành công");
                return response.data;
            }
        }).catch((error) => {
            error.response.data.map((item: string) => toast.error(item));
            return null;
        });
    }

    async delete(id: number): Promise<boolean> {
        return await axios.delete("/api/category/delete/" + id).then((response) => {
            return true;
        }).catch((error) => {
            toast.error("Có lỗi xảy ra, vui lòng thử lại sau");
            return false;
        });
    }

    async findById(id: number): Promise<Category> {
        return await axios.get("/api/category/get/" + id).then((response) => {
            return response.data;
        });
    }

    async update(category: Category): Promise<boolean> {
        return await axios.put("/api/category/update/" + category.id, category).then((response) => {
            return true;
        }).catch((error) => {
            toast.error("Có lỗi xảy ra, vui lòng thử lại sau");
            return false;
        });
    }

    async findByName(name: string): Promise<Category> {
        return await axios.get("/api/category/find/" + name).then((response) => {
            return response.data;
        });
    }
}