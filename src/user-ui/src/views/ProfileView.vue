<template>
	<div class="container">
		<section class="py-5 my-5">
			<div class="container">
				<h1 class="mb-5">Thông tin cá nhân</h1>
				<div class="bg-white shadow rounded-lg d-block d-sm-flex">
					<div class="profile-tab-nav border-right">
						<div class="p-4">
							<div class="img-circle text-center mb-3">
								<img
									:src="user.avatar === '' ? 'http://localhost/image/default.jpg' :'http://localhost/image/user/' + user.avatar"
									alt="Image" class="shadow">
							</div>
							<small v-if="user.avatar === null" class="text-danger">Bạn chưa có ảnh</small>
							<br>
							<form @submit.prevent="uploadFileData()">
								<div class="form-group">
									<!-- <label for="fileInput">Select file:</label> -->
									<input type="file" id="file" class="form-control-file">
								</div>
								<button type="submit" class="btn btn-primary">Cập nhật</button>
							</form>
							<br>
							<br>
							<h4 class="text-center">{{ user.fullName }}</h4>
						</div>
						<div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist"
						     aria-orientation="vertical">
							<a class="nav-link d-flex align-items-center" id="account-tab" data-bs-toggle="pill"
							   data-bs-target="#account"
							   role="tab" aria-controls="account" aria-selected="true">
								<i class="bi bi-person-circle me-2"></i>
								<span class="d-flex align-items-center">Tài khoản</span>
							</a>
							<a class="nav-link d-flex align-items-center" id="password-tab" data-bs-toggle="pill"
							   data-bs-target="#password"
							   role="tab" aria-controls="password" aria-selected="false">
								<i class="bi bi-key-fill me-2"></i>
								<span class="d-flex align-items-center">Mật khẩu</span>
							</a>
							<a class="nav-link d-flex align-items-center" id="history-tab" data-bs-toggle="pill"
							   data-bs-target="#history"
							   role="tab" aria-controls="history" aria-selected="false">
								<i class="bi bi-receipt me-2"></i>
								<span class="d-flex align-items-center">Lịch sử mua hàng</span>
							</a>
							<router-link to="/" class="nav-link d-flex align-items-center">
								<i class="bi bi-house-door-fill me-2"></i>
								Quay về trang chủ
							</router-link>
						</div>
					</div>
					<div class="tab-content p-4 p-md-5" id="v-pills-tabContent">
						<div class="tab-pane fade show active" id="account" role="tabpanel"
						     aria-labelledby="account-tab">
							<h3 class="mb-4">Tài khoản</h3>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label>Họ tên</label>
										<input type="text" class="form-control" v-model="user.fullName">
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label>Địa chỉ</label>
										<input type="text" class="form-control" v-model="user.address">
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label>Email</label>
										<input type="text" class="form-control" :value="user.email" disabled>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label>Số điện thoại</label>
										<input type="text" class="form-control" v-model="user.phoneNumber"
										       :placeholder="'Bạn chưa đặt số điện thoại'">
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label>Ngày tham gia</label>
										<input type="text" class="form-control" :value="formatDate(user.created)"
										       disabled>
									</div>
								</div>
							</div>
							<div>
								<button class="btn btn-primary" @click.prevent="update">Cập nhật</button>
							</div>
						</div>
						<div class="tab-pane fade" id="password" role="tabpanel" aria-labelledby="password-tab">
							<h3 class="mb-4">Đổi mật khẩu</h3>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label>Mật khẩu cũ</label>
										<input type="password" class="form-control"
										       v-model="changePasswordRequest.oldPassword">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label>Mật khẩu mới</label>
										<input type="password" class="form-control"
										       v-model="changePasswordRequest.newPassword">
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label>Nhập lại mật khẩu</label>
										<input type="password" class="form-control"
										       v-model="changePasswordRequest.confirmPassword">
									</div>
								</div>
							</div>
							<div>
								<button class="btn btn-primary" @click.prevent="changePassword">Cập nhật</button>
							</div>
						</div>
						<div class="tab-pane fade" id="history" role="tabpanel" aria-labelledby="history-tab">
							<h3 class="mb-4">history Settings</h3>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label>Login</label>
										<input type="text" class="form-control">
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label>Two-factor auth</label>
										<input type="text" class="form-control">
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="form-check">
											<input class="form-check-input" type="checkbox" value="" id="recovery">
											<label class="form-check-label" for="recovery">
												Recovery
											</label>
										</div>
									</div>
								</div>
							</div>
							<div>
								<button class="btn btn-primary">Update</button>
								<button class="btn btn-light">Cancel</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
</template>

<script lang="ts">
import {ChangePasswordRequest, UploadFileRequest, User} from '@/core/model/user.model';
import {defineComponent} from 'vue';
import {toast} from 'vue3-toastify';
import axios from 'axios';
import {UserService} from '@/core/service/user.service';

export default defineComponent({
	name: 'Profile Settings',
	data() {
		return {
			user: JSON.parse(JSON.stringify(localStorage.getItem("user"))),
			changePasswordRequest: {} as ChangePasswordRequest,
			uploadFile: {} as UploadFileRequest,
			userService: new UserService()
		}
	},
	methods: {
		formatDate(date: string) {
			return new Date(date).toLocaleDateString();
		},
		async changePassword() {
			this.userService.changePassword(this.changePasswordRequest);
		},
		async uploadFileData() {
			const file = document.getElementById('file') as HTMLInputElement;
			const formData = new FormData();
			if (file && file.files && file.files.length > 0) {
				formData.append('file', file.files[0]);
				try {
					const response = await axios.post('api/user/upload', formData, {
						headers: {
							'Content-Type': 'multipart/form-data',
							'Authorization': localStorage.getItem('access_token')
						}
					});
					if (response.status == 200) {
						toast.success("Cập nhật ảnh đại diện thành công");
						this.user.avatar = response.data;
						localStorage.setItem('user', JSON.stringify(this.user));
					}
				} catch (error: any) {
					toast.error(error.response.data);
				}
			}
		},
		async update() {
			try {
				const response = await axios.put('api/user/update/' + this.user.id, this.user);
				if (response.status == 200) {
					toast.success('Cập nhật thông tin thành công');
					localStorage.setItem('user', JSON.stringify(this.user));
				}
			} catch (error) {
				toast.error('Cập nhật thông tin thất bại');
			}
		}
	},
	created() {
		this.user = JSON.parse(this.user) as User;
		this.changePasswordRequest.username = this.user.username;
	}

})

</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap");

body {
	background: #f9f9f9;
	font-family: "Roboto", sans-serif;
}

.shadow {
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1) !important;
}

.profile-tab-nav {
	min-width: 250px;
}

.tab-content {
	flex: 1;
}

.form-group {
	margin-bottom: 1.5rem;
}

.nav-pills a.nav-link {
	padding: 15px 20px;
	border-bottom: 1px solid #ddd;
	border-radius: 0;
	color: #333;
}

.nav-pills a.nav-link i {
	width: 20px;
}

.img-circle img {
	height: 100px;
	width: 100px;
	border-radius: 100%;
	border: 5px solid #fff;
}</style>