import api from "../services/api";
import {getAuthHeader} from "../services/auth";
import {CategoryPostBody, CategoryPutBody} from "../interfaces/categoryInterface";
import {Pageable} from "../interfaces/page";
import {AxiosResponse} from "axios";

export default class CategoryController implements Pageable {
    findAll() {
        return api.get('/categories', {headers: getAuthHeader()});
    }

    post(data: CategoryPostBody) {
        return api.post('/categories', data, {headers: getAuthHeader()});
    }

    findById(id: string) {
        return api.get('/categories/' + id, {headers: getAuthHeader()});
    }

    put(data: CategoryPutBody) {
        console.log(data)
        return api.put('/categories/' + data.id, data, {headers: getAuthHeader()});
    }

    delete(id: string) {
        return api.delete('/categories/' + id, {headers: getAuthHeader()});
    }

    findByName(search:string, page:number, size?:number) {
        return api.get(`/categories/search?name=${search}&page=${page}&size=${size}`, {headers: getAuthHeader()});
    }

    findAllPageable(size: number, page: number) {
        return api.get(`/categories?size=${size}&page=${page}`, {headers: getAuthHeader()});
    }

    getAllPageable(page: number, size:number = 20): Promise<AxiosResponse> {
        return api.get(`/categories?size=${size}&page=${page}`, {headers: getAuthHeader()});
    };

    //TODO: delete vários e mover categoria
}