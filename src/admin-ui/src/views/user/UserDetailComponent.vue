<template>
	<div class="h2">Người dùng</div>
	<form @submit.prevent="addUser()" class="form-control">
		<div class="form-floating mb-3">
			<input type="text" class="form-control" id="floatingUsername" v-model="user.username" :readonly="isUpdate">
			<label for="floatingUsername">Tài khoản</label>
		</div>
		<div class="form-floating mb-3">
			<input type="email" class="form-control" id="floatingInput" placeholder="name@example.com"
			       v-model="user.email" :readonly="isUpdate">
			<label for="floatingInput">Email</label>
		</div>
		<div class="form-floating mb-3">
			<input type="password" class="form-control" id="floatingPassword"
			       placeholder="Password" v-model="user.password" :hidden="isUpdate">
			<label for="floatingPassword">Mật khẩu</label>
		</div>
		<div class="form-floating mb-3">
			<input type="text" class="form-control" id="floatingAddress" placeholder="Password"
			       v-model="user.address">
			<label for="floatingAddress">Địa chỉ</label>
		</div>
		<div class="form-floating mb-3">
			<input type="text" class="form-control" id="floatingName" placeholder="Password"
			       v-model="user.fullName">
			<label for="floatingName">Họ tên</label>
		</div>
		<div class="form-floating mb-3">
			<input type="text" class="form-control" id="floatingPhone" v-model="user.phoneNumber">
			<label for="floatingPhone">Số điện thoại</label>
		</div>
		<button type="submit" class="btn btn-success d-flex justify-content-start">Lưu</button>
	</form>
</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {User} from "@/core/model/user.model";
import {UserService} from "@/core/service/user.service";

export default defineComponent({
	name: 'UserDetailComponent',
	data() {
		return {
			userService: new UserService(),
			user : new User(),
			id : 0 as number,
			isUpdate : false as boolean
		}
	},
	methods: {
		addUser() {
			if(this.isUpdate){
				this.userService.updateUser(this.user);
			}else{
				this.userService.register(this.user);
				this.$router.push('/admin/user');
			}
		},
		openModal() {
			document.getElementById('show-modal')?.click();
		}
	},
	created() {
		this.id = Number.parseInt(this.$route.params.id as string);
		if (this.id != 0 && !isNaN(this.id)) {
			this.isUpdate = true;
			this.userService.getUserById(this.id).then((res) => {
				this.user = res;
			});
		}
	}
})
</script>