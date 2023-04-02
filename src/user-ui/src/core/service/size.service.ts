import axios from 'axios'
import {Size} from "@/core/model/size.model";
export class SizeService{
    url = '/api/size'

    async findAll(): Promise<Array<Size>>{
        const response = await axios.get(this.url + '/findAll')
        return response.data
    }
}