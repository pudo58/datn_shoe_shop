<template>
  <!-- Button trigger modal -->
  <button v-show="false" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#list-attribute" id="show-modal-attr">
    Launch static backdrop modal
  </button>

  <!-- Modal -->
  <div class="modal hide" id="list-attribute"  aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">Danh sách thuộc tính</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
         <div class="table-responsive">
           <table class="table table-striped table-hover table-bordered">
             <thead>
               <tr>
                 <th>
                    <input type="checkbox" class="form-check-input" id="checkAll" @change="checkAll()">
                 </th>
                  <th>STT</th>
                  <th>Tên thuộc tính</th>
                  <th>Kiểu dữ liệu</th>
               </tr>
             </thead>
             <tbody>
                <tr v-for="(item,index) in attributeList?.content" :key="index">
                  <td>
                    <input type="checkbox" :value="item.id" class="form-check-input" id="check" v-model="attributeIdList">
                  </td>
                  <td>{{index+1}}</td>
                  <td>{{item.name}}</td>
                  <td>{{item.type}}</td>
                </tr>
             </tbody>
           </table>
         </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" @click.prevent="addAll">Thêm</button>
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
        <hr>
        <div class="text-center h3">Thuộc tính đã thêm</div>
        <div>
          <div class="table-responsive">
            <table class="table table-striped table-hover table-bordered">
              <thead>
              <tr>

                <th>STT</th>
                <th>Tên thuộc tính</th>
                <th>Kiểu dữ liệu</th>
                <th>
                  Thao tác
                </th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(item,index) in attributeCurrentList" :key="index">
                <td>{{index+1}}</td>
                <td>{{item.name}}</td>
                <td>{{item.type}}</td>
                <td>
                    <i role="button" @click.prevent="deleteAttributeRequest(item.id)" class="bi bi-trash"></i>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
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
      </div>
    </div>
  </div>
</template>

<script lang="ts">

import { defineComponent } from 'vue'
import { toast } from 'vue3-toastify';
import {Attribute, AttributeAddAllRequest, DeleteAttributeRequest} from "@/core/model/attribute.model";
import {AttributeService} from "@/core/service/attribute.service";
import {Pageable} from "@/core/model/core.base";

export default defineComponent({
  name: 'AttributeListComponent',
  data() {
    return {
      attributeService: new AttributeService(),
      openModalDialog : false,
      attributeList : new Pageable<Attribute>(),
      page : 0,
      size : 10,
      attributeIdList : [] as number[],
      attributeRequest : new AttributeAddAllRequest(),
      attributeCurrentList : [] as Attribute[],
      deleteAttribute : new DeleteAttributeRequest(),
      categoryId : null
    }
  },
  props: {
    attribute: {
      type: Attribute,
      required: true
    },
  },
  methods: {
    getAttribute(page:number,size : number){
      this.attributeService.findAll(page,size).then(response => {
        this.attributeList = response;
      }).catch(error => {
        toast.error(error.message);
      })
    },
    onChange(){
      this.getAttribute(this.page,this.size);
    },
    openModal(){
      document.getElementById('show-modal-attr')?.click();
    },
    checkAll(){
      let checkAll = document.getElementById('checkAll') as HTMLInputElement;
      let check = document.getElementsByClassName('form-check-input') as HTMLCollectionOf<HTMLInputElement>;
      if(checkAll.checked){
        for(let i = 0; i < check.length; i++){
          check[i].checked = true;
        }
      }else{
        for(let i = 0; i < check.length; i++){
          check[i].checked = false;
        }
      }
    },
    addAll(){
      this.attributeRequest.categoryId = this.categoryId;
      this.attributeRequest.attributeIdList = this.attributeIdList;
      this.attributeService.addAll(this.attributeRequest).then(response => {
        this.findAttributeByCategoryId(this.categoryId);
      }).catch(error => {
        toast.error(error.message);
      });
    },
    findAttributeByCategoryId(categoryId : number){
      this.categoryId = categoryId;
      this.attributeService.findAllByCategoryId(categoryId).then(response => {
        this.attributeCurrentList = response;
      }).catch(error => {
        toast.error(error.message);
      })
    },
    deleteAttributeRequest(attributeId: number){
      this.deleteAttribute.attributeId = attributeId;
      this.deleteAttribute.categoryId = this.categoryId;
      this.attributeService.deleteAttribute(this.deleteAttribute).then(response => {
        this.findAttributeByCategoryId(this.categoryId);
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

</style>