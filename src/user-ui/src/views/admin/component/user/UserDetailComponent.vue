<template>
  <!-- Button trigger modal -->
  <button v-show="false" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#create-user" id="show-modal">
    Launch static backdrop modal
  </button>

  <!-- Modal -->
  <div class="modal hide" id="create-user"  aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">Modal title</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
         <form @submit.prevent="addUser()">
           <div class="form-floating mb-3">
             <input type="text" class="form-control" id="floatingUsername" v-model="user.username" :disabled="isUpdate">
             <label for="floatingUsername">Tài khoản</label>
           </div>
           <div class="form-floating mb-3">
             <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com" v-model="user.email" :disabled="isUpdate">
             <label for="floatingInput">Email</label>
           </div>
           <div class="form-floating mb-3">
             <input v-if="!isUpdate" type="password" class="form-control" id="floatingPassword" placeholder="Password" v-model="user.password">
             <label for="floatingPassword">Mật khẩu</label>
           </div>
           <div class="form-floating mb-3">
             <input type="text" class="form-control" id="floatingAddress" placeholder="Password" v-model="user.address">
             <label for="floatingAddress">Địa chỉ</label>
           </div>
           <div class="form-floating mb-3" >
             <input type="text" class="form-control" id="floatingName" placeholder="Password" v-model="user.fullName">
             <label for="floatingName">Họ tên</label>
           </div>
           <div class="form-floating mb-3">
             <input type="text" class="form-control" id="floatingPhone" v-model="user.phoneNumber">
             <label for="floatingPhone">Số điện thoại</label>
           </div>
           <button type="submit" class="btn btn-success">Lưu</button>
         </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import {User} from "@/core/model/user.model";
import {UserService} from "@/core/service/user.service";
export default defineComponent({
  name: 'UserDetailComponent',
  data() {
    return {
      userService: new UserService(),
      openModalDialog : false
    }
  },
  props: {
    user: {
      type: User,
      required: true
    },
    isUpdate: {
      type: Boolean,
      required: false,
      default: false
    }
  },
  methods: {
    addUser() {
      this.userService.register(this.user);
      this.$emit('added-user',this.user);
    },
    openModal(){
      document.getElementById('show-modal')?.click();
    }
  }
})
</script>