<template>
	<section class="vh-100" style="background-color: #508bfc;">
		<div class="loading" v-if="loading">
            <div class="spinner-border text-primary spinner-center" role="status">
            </div>
        </div>
		<div class="container py-5 h-100">
			<div class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-12 col-md-8 col-lg-6 col-xl-5">
					<div class="card shadow-2-strong" style="border-radius: 1rem;">
						<div class="card-body p-5 text-center">
							<h3 class="mb-5">Đăng nhập</h3>
							<form @submit.prevent="login">
								<div class="form-floating mb-4">
									<input type="text" id="typeEmailX-2" class="form-control form-control-lg"
										v-model="user.username" />
									<label class="form-label" for="typeEmailX-2">Tài khoản</label>
								</div>

								<div class="form-floating mb-4">
									<input type="password" id="typePasswordX-2" class="form-control form-control-lg"
										v-model="user.password" />
									<label class="form-label" for="typePasswordX-2">Mật khẩu</label>
								</div>

								<!-- Checkbox -->
								<div class="form-check d-flex justify-content-start mb-4">
									<input class="form-check-input" type="checkbox" value="" id="form1Example3" />
									<label class="form-check-label" for="form1Example3"> Nhớ tôi</label>
								</div>

								<button class="btn btn-primary">Đăng nhập</button>

							</form>
							<hr class="my-4">
							<button type="button" role="button" class="btn btn-outline-warning">
								<router-link to="/forgot-password">Quên mật khẩu</router-link>
							</button>
							<button type="button" role="button" class="btn btn-outline-success">
								<router-link to="/register">Tạo tài khoản</router-link>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</template>

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

</style>

<script lang="ts">
import { UserService } from '@/core/service/user.service';
import router from '@/router';
import { ref } from 'vue';
import { toast } from "vue3-toastify";
export default {
	name: 'LoginView',
	setup() {
		const userService = new UserService();
		var loading = ref(false);
		const user = ref({
			username: '',
			password: ''
		});
		const login = function (){
			loading.value = true;
			if(userService.login(user.value.username, user.value.password)){
        setTimeout(() => {
          loading.value = false;
        }, 1000);
        router.push('/')
      }else{
        setTimeout(() => {
          loading.value = false;
        }, 1000);
        toast.error("Tài khoản hoặc mật khẩu không đúng");
      }
		}
		return {
			user,
			login,
			loading
		}
	}
}
</script>