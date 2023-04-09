import axios from "axios";

export class ProductDetailService {
    url = "/api/product-detail";

    async findAll(page: number, size: number) {
        const response = await axios.get(this.url + "/page/" + page + "/" + size);
        return response.data;
    }
}