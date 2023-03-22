<template>
  <div class="row">
    <p class="h2">Quản lý danh mục</p>
  </div>
  <hr>
  <div class="row">
    <div class="input-group mb-3 col">
      <input type="text" class="form-control" placeholder="Mời bạn nhập tên danh mục " v-model="find">
      <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click.prevent="findByName()">
        <i class="bi bi-search"></i>
        Tìm kiếm
      </button>
    </div>
    <div class="col"></div>
    <div class="col">
      <button class="btn btn-success m-1" @click.prevent="$refs.attributeDetail.openModal();isUpdate=false;attribute = {}">
        <i class="bi bi-pencil-fill"></i>
        Thêm mới
      </button>
      <button class="btn btn-danger m-1">
        <i class="bi bi-trash-fill"></i>
        Xóa
      </button>
    </div>
  </div>
  <div class="table table-responsive" id="table">
    <table class="table table-bordered table-hover table-striped">
      <thead>
      <tr>
        <th>Tên danh mục</th>
        <th>Ngày tạo</th>
        <th>Mô tả</th>
        <th>Trạng thái</th>
        <th>Thao tác</th>
      </tr>
      </thead>
      <tbody>
      <tr v-if="attributeList.content?.length > 0" class="align-middle" v-for="item in attributeList.content">
        <td>{{item.name}}</td>
        <td>{{dateTime(item.created+'')}}</td>
        <td>{{item.description}}</td>
        <td>
          <span v-if="item.isTrash === false" class="badge bg-success">Hoạt động</span>
          <span v-else class="badge bg-danger">Khóa</span>
        </td>
        <td>
          <button class="btn btn-success btn-sm m-1" @click.prevent="$refs.attributeDetail.openModal();attribute = item;">
            <i class="bi bi-pencil-fill"></i>
          </button>
          <button class="btn btn-danger btn-sm m-1" @click.prevent="deleteById(item.id)">
            <i class="bi bi-trash-fill"></i>
          </button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
  <div class="row">
    <nav class="col">
      <ul class="pagination">
        <li class="page-item">
          <a class="page-link" @click.prevent="getattribute(page--,size)">Previous</a>
        </li>
        <li class="page-item" v-for="item in attributeList?.totalPages">
          <a class="page-link" href="#" @click="getattribute(item-1,size)">{{item}}</a>
        </li>
        <li class="page-item">
          <a class="page-link" href="#" @click.prevent="getattribute(page++,size)">Next</a>
        </li>
      </ul>
      <div class="p-2 col pagination">
        Số phần tử
        <select v-model="size" @change.prevent="onChange()">
          <option :value="10">10</option>
          <option :value="25">25</option>
          <option :value="50">50</option>
        </select>
      </div>
    </nav>
  </div>
  <attribute-detail-component :attribute="attribute" ref="attributeDetail" @added-attribute="attributeAdded"></attribute-detail-component>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { toast } from 'vue3-toastify';
import {Pageable} from "@/core/model/core.base";
import {attribute} from "@/core/model/attribute.model";
import {attributeService} from "@/core/service/attribute.service";
import {User, UserFindRequest} from "@/core/model/user.model";
import moment from 'moment/moment';
import attributeDetailComponent from "@/views/admin/component/attribute/attributeDetailComponent.vue";

export  default defineComponent({
  name : "attributeService",
  components : {
    attributeDetailComponent
  },
  data(){
    return {
      attributeList : new Pageable<attribute>(),
      attribute : new attribute(),
      loading : false,
      search : '' as string,
      attributeService : new attributeService(),
      page : 0 as number,
      size : 10 as number,
      find : '' as string,
    }
  },
  methods : {
    getAttribute(page:number,size : number){
      this.attributeService.findAll(page,size).then(response => {
        this.attributeList = response.data;
      }).catch(error => {
        toast.error(error.message);
      })
    },
    dateTime(value : string) {
      if(value == null || value == undefined || value === ''){
        return '';
      }else{
        return moment(value).format('DD-MM-YYYY HH:mm:ss');
      }

    },
    onChange(){
      this.getattribute(this.page,this.size);
    },
    deleteById(id : number){
      if(confirm("Bạn có chắc chắn muốn xóa không?")){
        this.attributeService.delete(id).then(data => {
          if(data === true){
            toast.success("Xóa thành công");
            this.getattribute(this.page,this.size);
          }
        }).catch(error => {
          toast.error(error.message);
        })
      }
    },attributeAdded(attribute : attribute){
      this.attributeList.content?.push(attribute);
    },
    findByName(){
      if(this.find === '' || this.find == null || this.find == undefined){
        this.getattribute(this.page,this.size);
        return;
      }
      this.attributeService.findByName(this.find).then(response => {
        this.attributeList.content = [];
        this.attributeList.content.push(response);
      }).catch(error => {
        toast.error(error.message);
      })
    }
  },
  created() {
    this.getattribute(this.page,this.size);
  }
})
</script>

<style scoped>
#table{
  padding: 0px;
}
</style>