import axios from "axios";
import {toast} from "vue3-toastify";
import {Pageable} from "@/core/model/core.base";
import {Attribute, AttributeAddAllRequest, DeleteAttributeRequest} from "@/core/model/attribute.model";

export class AttributeService {
    url = "/api/attribute/";

    async findAll(page: number, size: number): Promise<Pageable<Attribute>> {
        const response = await axios.get(this.url + "page/" + page + "/" + size);
        return response.data;
    }

    async save(attribute: Attribute): Promise<Attribute> {
        return await axios.post(this.url + "add", attribute).then((response) => {
            if (response.data !== null) {
                toast.success("Thêm mới thành công");
                return response.data;
            }
        }).catch((error) => {
            error.response.data.map((item: string) => toast.error(item));
            return null;
        });
    }

    delete(id: number): Promise<boolean> {
        return axios.delete(this.url + "delete/" + id).then((response) => {
            return true;
        }).catch((error) => {
            toast.error("Có lỗi xảy ra, vui lòng thử lại sau");
            return false;
        });
    }

    async findByName(name: string): Promise<Attribute> {
        return await axios.get(this.url + "find/" + name).then((response) => {
            return response.data;
        });
    }

    async findById(id: number): Promise<Attribute> {
        return await axios.get(this.url + "get/" + id).then((response) => {
            return response.data;
        });
    }

    async update(attribute: Attribute): Promise<boolean> {
        return await axios.put(this.url + "update/" + attribute.id, attribute).then((response) => {
            return true;
        }).catch((error) => {
            toast.error("Có lỗi xảy ra, vui lòng thử lại sau");
            return false;
        });
    }

    async addAll(request: AttributeAddAllRequest): Promise<Array<Attribute>> {
        const response = await axios.post(this.url + "addAll", request);
        if (response.data !== null) {
            toast.success("Thêm mới thành công");
        }
        return response.data;
    }

    async findAllByCategoryId(id: number): Promise<Array<Attribute>> {
        const response = await axios.get(this.url + "findByCategoryId/" + id);
        return response.data;
    }

    async deleteAttribute(request: DeleteAttributeRequest): Promise<boolean> {
        const response = await axios.post(this.url + "deleteAttributeRequest", request);
        toast.success("Xóa thành công");
        return response.data;
    }
}