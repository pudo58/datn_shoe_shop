import axios from "axios";
import {toast} from "vue3-toastify";
import {Brand, BrandResponse} from "@/core/model/brand.model";

export class BrandService {
    url = "/api/brand/";

    async findAll(page: number, size: number): Promise<Array<Brand>> {
        const response = await axios.get(this.url + "page/" + page + "/" + size);
        return response.data;
    }

    async save(publisher: Brand): Promise<Brand> {
        if (publisher.name === undefined || publisher.name === null || publisher.name === "") {
            toast.error("Tên nhà xuất bản không được để trống");
            return new Brand();
        } else if (publisher.website === undefined || publisher.website === null || publisher.website === "") {
            toast.error("Website nhà xuất bản không được để trống");
            return new Brand();
        }
        return await axios.post(this.url + "add", publisher).then((response) => {
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
        return await axios.delete(this.url + "delete/" + id).then((response) => {
            return true;
        }).catch((error) => {
            toast.error("Có lỗi xảy ra, vui lòng thử lại sau");
            return false;
        });
    }

    async findById(id: number): Promise<Brand> {
        return await axios.get(this.url + "get/" + id).then((response) => {
            return response.data;
        });
    }

    async update(publisher: Brand): Promise<boolean> {
        return await axios.put(this.url + "update/" + publisher.id, publisher).then((response) => {
            return true;
        }).catch((error) => {
            toast.error("Có lỗi xảy ra, vui lòng thử lại sau");
            return false;
        });
    }

    async findByName(name: string): Promise<Brand> {
        return await axios.get(this.url + "getByName/" + name).then((response) => {
            return response.data;
        });
    }

    async findAllPublisher(): Promise<Brand[]> {
        return await axios.get(this.url + "findAll").then((response) => {
            return response.data;
        });
    }

    async findAllPublisherData(): Promise<BrandResponse[]> {
        return await axios.get(this.url + "findAllData").then((response) => {
            return response.data;
        });
    }
}