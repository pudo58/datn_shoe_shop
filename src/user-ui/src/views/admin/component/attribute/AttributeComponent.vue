<template>
  <div class="row">
    <p class="h2">Quản lý thuộc tính</p>
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
      <button class="btn btn-success m-1" @click.prevent="$refs.attributeDetail.openModal();attribute = {}">
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
        <th>Tên thuộc tính</th>
        <th>Kiểu dữ liệu</th>
        <th>Trạng thái</th>
        <th>Thao tác</th>
      </tr>
      </thead>
      <tbody>
      <tr class="align-middle" v-for="item in attributeList?.content">
        <td>{{item?.name}}</td>
        <td>{{item?.type}}</td>
        <td>{{dateTime(item?.created+'')}}</td>
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
          <a class="page-link" @click.prevent="getAttribute(page--,size)">Previous</a>
        </li>
        <li class="page-item" v-for="item in attributeList?.totalPages">
          <a class="page-link" href="#" @click="getAttribute(item-1,size)">{{item}}</a>
        </li>
        <li class="page-item">
          <a class="page-link" href="#" @click.prevent="getAttribute(page++,size)">Next</a>
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
import {Category} from "@/core/model/category.model";;
import moment from 'moment/moment';
import {Attribute} from "@/core/model/attribute.model";
import {AttributeService} from "@/core/service/attribute.service";
import AttributeDetailComponent from "@/views/admin/component/attribute/AttributeDetailComponent.vue";

export  default defineComponent({
  name : "AttributeComponent",
  components : {
    AttributeDetailComponent,
  },
  data(){
    return {
      attributeList : new Pageable<Attribute>(),
      attribute : new Category(),
      loading : false,
      search : '' as string,
      attributeService : new AttributeService(),
      page : 0 as number,
      size : 10 as number,
      find : '' as string,
    }
  },
  methods : {
    getAttribute(page:number,size : number){
      this.attributeService.findAll(page,size).then(response => {
        this.attributeList = response;
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
      this.getAttribute(this.page,this.size);
    },
    deleteById(id : number){
      if(confirm("Bạn có chắc chắn muốn xóa không?")){
        this.attributeService.delete(id).then(data => {
          if(data === true){
            toast.success("Xóa thành công");
            this.getAttribute(this.page,this.size);
          }
        }).catch(error => {
          toast.error(error.message);
        })
      }
    },attributeAdded(attribute : Attribute){
      this.attributeList.content?.push(attribute);
    },
    findByName(){
      if(this.find === '' || this.find == null || this.find == undefined){
        this.getAttribute(this.page,this.size);
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
    this.getAttribute(this.page,this.size);
  }
})
</script>

<style scoped>
#table{
  padding: 0px;
}
</style>