import axios from "axios";
export class VoucherService{
    url = "/api/voucher/";

    async finAllByUserId(id: number){
        const response = await axios.get(this.url + "findAllByUserId/" + id);
        return response.data;
    }
}