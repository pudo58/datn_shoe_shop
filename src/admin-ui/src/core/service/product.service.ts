import axios from "axios";
import {Product, ProductDto, ProductSearchRequest} from "@/core/model/product.model";
import {toast} from "vue3-toastify";

export class ProductService {
    url = "/api/product/";

    async findAll(page: number, size: number) {
        const response = await axios.get(this.url + "page/" + page + "/" + size);
        return response.data;
    }

    async findById(id: number) {
        const response = await axios.get(this.url + "get/"+ id);
        return response.data;
    }

    async save(productDto: ProductDto) {
        const response = await axios.post(this.url + "addDetail", productDto);
        try {
            if (response.data !== null && response.data.status == 200) {
                toast.success("Thêm sản phẩm thành công");
                return response.data;
            }else if(response?.data?.status == 500){
                toast.error(response.data.message);
            }
        } catch (e) {
            toast.error("Thêm sản phẩm thất bại");
        }
    }

    async update(id: number, product: Product) {
        const response = await axios.put(this.url + "update/" + id, product);
        try {
            if (response.data !== null) {
                toast.success("Cập nhật sản phẩm thành công");
                return response.data;
            }
        } catch (e) {
            toast.error("Cập nhật sản phẩm thất bại");
        }
    }

    async addImage(id: number, file: File) {
        const formData = new FormData();
        formData.append('file', file);
        const response = await axios.post(this.url + "addImage?id=" + id, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
        try {
            if (response.data !== null) {
                return response.data;
            }
        } catch (e) {
            toast.error("Thêm ảnh thất bại");
        }
    }

    async delete(id: number) {
        if (confirm("Bạn có muốn xóa sản phẩm không ?")) {
            const response = await axios.delete(this.url + "delete/" + id);
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

    async findBySearch(model : ProductSearchRequest) {
        const response = await axios.post(this.url + "findBySearch", model);
        return response.data;
    }

    async findByName(name: string) :Promise<Product[]> {
        const response = await axios.get(this.url + "findByName/" + name);
        return response.data;
    }

}

