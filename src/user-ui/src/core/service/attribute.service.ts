import axios from "axios";
import { toast} from "vue3-toastify";
import {Pageable} from "@/core/model/core.base";
import {Attribute} from "@/core/model/attribute.model";
export class AttributeService{
    findAll(page: number, size: number): Promise<Pageable<Attribute>> {
        return axios.get("/api/attribute/page/" + page + "/" + size);
    }
}