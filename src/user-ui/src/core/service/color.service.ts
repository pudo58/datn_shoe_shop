import axios from "axios";

export class ColorService{
    url = "/api/color/";

    async findAll(){
        const response = await axios.get(this.url + "findAll");
        return response.data;
    }
}