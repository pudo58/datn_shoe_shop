import axios from "axios";
import {toast} from "vue3-toastify";
import {Publisher} from "@/core/model/publisher.model";

export class PublisherService {
    async findAll(page: number, size: number): Promise<Array<Publisher>> {
        const response = await axios.get("/api/publisher/page/" + page + "/" + size);
        return response.data;
    }

    async save(publisher: Publisher): Promise<Publisher> {
        if (publisher.name === undefined || publisher.name === null || publisher.name === "") {
            toast.error("Tên nhà xuất bản không được để trống");
            return new Publisher();
        } else if (publisher.website === undefined || publisher.website === null || publisher.website === "") {
            toast.error("Website nhà xuất bản không được để trống");
            return new Publisher();
        }
        return await axios.post("/api/publisher/add", publisher).then((response) => {
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
        return await axios.delete("/api/publisher/delete/" + id).then((response) => {
            return true;
        }).catch((error) => {
            toast.error("Có lỗi xảy ra, vui lòng thử lại sau");
            return false;
        });
    }

    async findById(id: number): Promise<Publisher> {
        return await axios.get("/api/publisher/get/" + id).then((response) => {
            return response.data;
        });
    }

    async update(publisher: Publisher): Promise<boolean> {
        return await axios.put("/api/publisher/update/" + publisher.id, publisher).then((response) => {
            return true;
        }).catch((error) => {
            toast.error("Có lỗi xảy ra, vui lòng thử lại sau");
            return false;
        });
    }

    async findByName(name: string): Promise<Publisher> {
        return await axios.get("/api/publisher/getByName/" + name).then((response) => {
            return response.data;
        });
    }

    async findAllPublisher(): Promise<Publisher[]> {
        return await axios.get("/api/publisher/findAll").then((response) => {
            return response.data;
        });
    }
}