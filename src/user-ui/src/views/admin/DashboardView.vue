<template>
  <div class="container-fluid">
    <div class="row flex-nowrap">
      <div class="col-auto col-md-3 col-xl-2 px-sm-2 px-0 bg-dark">
        <div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 text-white min-vh-100">
          <a href="/" class="d-flex align-items-center pb-3 mb-md-0 me-md-auto text-white text-decoration-none">
            <span class="fs-5 d-none d-sm-inline text-uppercase">Quản lý dữ liệu</span>
          </a>
          <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start" id="menu">
            <li class="nav-item">
              <a href="#" class="nav-link align-middle px-0">
                <i class="bi bi-bar-chart-line-fill"></i> <span class="ms-1 d-none d-sm-inline">Biểu đồ thống kê</span>
              </a>
            </li>
            <li>
              <a href="#submenu1" data-bs-toggle="collapse" class="nav-link px-0 align-middle">
                <i class="bi bi-database-fill"></i> <span class="ms-1 d-none d-sm-inline">Quản lý dữ liệu bảng</span> </a>
              <ul class="collapse show nav flex-column ms-1" id="submenu1" data-bs-parent="#menu">
                <li class="w-100" v-for="item in component">
                  <a class="nav-link px-0 d-flex m-3" @click.prevent="hideComponent(item.name)" role="button">
                    <span class="d-none d-sm-inline" :title="item.label">{{item.label}}</span> </a>
                </li>
              </ul>
            </li>
            <li>
              <a href="#" class="nav-link px-0 align-middle">
                <i class="fs-4 bi-table"></i> <span class="ms-1 d-none d-sm-inline">Orders</span></a>
            </li>
            <li>
              <a href="#submenu2" data-bs-toggle="collapse" class="nav-link px-0 align-middle ">
                <i class="fs-4 bi-bootstrap"></i> <span class="ms-1 d-none d-sm-inline">Bootstrap</span></a>
              <ul class="collapse nav flex-column ms-1" id="submenu2" data-bs-parent="#menu">
                <li class="w-100">
                  <a href="#" class="nav-link px-0"> <span class="d-none d-sm-inline">Item</span> 1</a>
                </li>
                <li>
                  <a href="#" class="nav-link px-0"> <span class="d-none d-sm-inline">Item</span> 2</a>
                </li>
              </ul>
            </li>
            <li>
              <a href="#submenu3" data-bs-toggle="collapse" class="nav-link px-0 align-middle">
                <i class="fs-4 bi-grid"></i> <span class="ms-1 d-none d-sm-inline">Products</span> </a>
              <ul class="collapse nav flex-column ms-1" id="submenu3" data-bs-parent="#menu">
                <li class="w-100">
                  <a href="#" class="nav-link px-0"> <span class="d-none d-sm-inline">Product</span> 1</a>
                </li>
                <li>
                  <a href="#" class="nav-link px-0"> <span class="d-none d-sm-inline">Product</span> 2</a>
                </li>
                <li>
                  <a href="#" class="nav-link px-0"> <span class="d-none d-sm-inline">Product</span> 3</a>
                </li>
                <li>
                  <a href="#" class="nav-link px-0"> <span class="d-none d-sm-inline">Product</span> 4</a>
                </li>
              </ul>
            </li>
            <li>
              <a href="#" class="nav-link px-0 align-middle">
                <i class="fs-4 bi-people"></i> <span class="ms-1 d-none d-sm-inline">Customers</span> </a>
            </li>
          </ul>
          <hr>
          <div class="dropdown pb-4">
            <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
              <img src="https://github.com/mdo.png" alt="hugenerd" width="30" height="30" class="rounded-circle">
              <span class="d-none d-sm-inline mx-1">loser</span>
            </a>
            <ul class="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser1">
              <li><a class="dropdown-item" href="#">New project...</a></li>
              <li><a class="dropdown-item" href="#">Settings</a></li>
              <li><a class="dropdown-item" href="#">Profile</a></li>
              <li>
                <hr class="dropdown-divider">
              </li>
              <li><a class="dropdown-item" href="#">Sign out</a></li>
            </ul>
          </div>
        </div>
      </div>
      <div class="col py-3">
        <user-component v-if="component[0].show"></user-component>
        <category-component v-if="component[1].show"></category-component>
        <publisher-component v-if="component[2].show"></publisher-component>
        <attribute-component v-if="component[4].show"></attribute-component>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import UserComponent from "@/views/admin/component/user/UserComponent.vue";
import CategoryComponent from "@/views/admin/component/category/CategoryComponent.vue";
import AttributeComponent from "@/views/admin/component/attribute/AttributeComponent.vue";
import PublisherComponent from "@/views/admin/component/publisher/PublisherComponent.vue";
export default defineComponent({
  name: "ProductView",
  components: {
    UserComponent,
    CategoryComponent,
    AttributeComponent,
    PublisherComponent
  },
  data() {
    return {
      component : [
        {name : 'user',show : false,label : 'Quản lý người dùng'},
        {name : 'category',show : false,label : 'Quản lý danh mục'},
        {name : 'publisher',show : false,label : 'Quản lý nhà sản xuất'},
        {name : 'product',show : false,label : 'Quản lý sản phẩm'},
        {name : 'cart',show : false,label : 'Quản lý đơn hàng'},
        {name : 'attribute',show : false,label : 'Quản lý thuộc tính'},
      ]
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {

    },
    hideComponent(name : string){
      this.component.forEach((item)=>{
        if(item.name === name){
          item.show = true;
        }else{
          item.show = false;
        }
      })
    }
  }

})
</script>

<style scoped>
a{
  color: #fff;
}
</style>