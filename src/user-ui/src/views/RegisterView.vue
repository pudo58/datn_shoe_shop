<template>
	<section class="vh-100" style="background-color: #eee;">
		<div class="loading" v-if="loading">
			<div class="spinner-border text-primary spinner-center" role="status">
			</div>
		</div>
		<div class="container h-100">
			<div class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-lg-12 col-xl-11">
					<div class="card text-black" style="border-radius: 25px;">
						<div class="card-body p-md-5">
							<div class="row justify-content-center">
								<div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">
									<p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Đăng ký</p>
									<form class="mx-1 mx-md-4" @submit.prevent="register()">
										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-user fa-lg me-3 fa-fw"></i>
											<div class="form-floating flex-fill mb-0">
												<input type="text" id="form3Example1c" class="form-control"
												       v-model="user.username"/>
												<label class="form-label" for="form3Example1c">Username</label>
											</div>
										</div>
										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-user fa-lg me-3 fa-fw"></i>
											<div class="form-floating flex-fill mb-0">
												<input type="text" id="form3Example2c" class="form-control"
												       v-model="user.fullName"/>
												<label class="form-label" for="form3Example2c">Họ tên</label>
											</div>
										</div>
										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
											<div class="form-floating flex-fill mb-0">
												<input type="email" id="form3Example3c" class="form-control"
												       v-model="user.email"/>
												<label class="form-label" for="form3Example3c">Email</label>
											</div>
										</div>
										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
											<div class="form-floating flex-fill mb-0">
												<input type="text" id="form3Example4c" class="form-control"
												       v-model="user.address"/>
												<label class="form-label" for="form3Example4c">Địa chỉ</label>
											</div>
										</div>
										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-key fa-lg me-3 fa-fw"></i>
											<div class="form-floating flex-fill mb-0">
												<input type="password" id="form3Example5cd" class="form-control"
												       v-model="user.password"/>
												<label class="form-label" for="form3Example5cd">Mật khẩu</label>
											</div>
										</div>

										<div class="d-flex flex-row align-items-center mb-4">
											<i class="fas fa-key fa-lg me-3 fa-fw"></i>
											<div class="form-floating flex-fill mb-0">
												<input type="password" id="form3Example6cd" class="form-control"
												       v-model="user.rePassword"/>
												<label class="form-label" for="form3Example6cd">Nhập lại mật
													khẩu</label>
											</div>
										</div>

										<div class="form-check d-flex justify-content-center mb-5">
											<input class="form-check-input me-2" type="checkbox" :value="accepp"
											       v-model="user.accept"
											       id="form2Example33"/>
											<label class="form-check-label" for="form2Example33">
												Tôi đồng ý với các điều khoản
											</label>
										</div>

										<div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
											<button id="btn" type="submit" class="btn btn-primary btn-lg">Đăng ký
											</button>
										</div>

									</form>

								</div>
								<div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

									<img src="../assets/draw2.webp" class="img-fluid" alt="Sample image">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</template>
<script lang="ts">
import {UserRequest} from '@/core/model/user.model';
import {defineComponent} from 'vue'
import {toast} from 'vue3-toastify'
import {UserService} from '@/core/service/user.service';

export default defineComponent({
	name: 'RegisterView',
	data() {
		return {
			user: {
				repassword: '',
				accept: false
			} as UserRequest,
			accepp: false,
			userService: new UserService(),
			loading: false
		}
	},
	methods: {
		register() {
			if (this.user.accept == false) {
				toast.error('Bạn chưa đồng ý với điều khoản');
				return;
			}
			if (this.user.password != this.user.rePassword) {
				toast.error('Mật khẩu không khớp');
				return;
			}
			this.loading = true;
			this.userService.register(this.user);
			setTimeout(() => {
				this.loading = false;
				this.$router.push('/login');
			}, 5000);
		}
	}

})
</script>
<style scoped>
.loading {
	background: rgba(0, 0, 0, .5);
	width: 100%;
	height: 100%;
	position: fixed;
	top: 0;
	left: 0;
	z-index: 999;
}

.spinner-center {
	top: 50%;
	left: 50%;
	position: absolute;
}

.title {
	font-size: 1.5rem;
}

small {
	display: flex;
	color: red;
}

.error {
	border: 1px solid red;

}

.modal {
	background: rgba(0, 0, 0, .5);
	width: 100%;
	height: 100%;
	position: fixed;
	top: 0;
	left: 0;
	z-index: 999;
}</style>