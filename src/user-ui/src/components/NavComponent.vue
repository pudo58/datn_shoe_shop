<template>
	<div class="container">
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">
				<router-link to="/" class="navbar-brand">Aboo Shop</router-link>
				<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
					aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarNav">
					<ul class="navbar-nav me-auto">
						<li class="nav-item">
							<router-link to="/" class="nav-link">Trang chủ</router-link>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">Sản phẩm</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">Khuyến mãi</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">Liên hệ</a>
						</li>
					</ul>
					<ul class="navbar-nav">
						<li class="nav-item" v-show="!isLogin">
							<router-link to="/login" class="nav-link">Đăng nhập</router-link>
						</li>
						<li class="nav-item" v-show="!isLogin">
							<router-link to="/register" class="nav-link">Đăng ký</router-link>
						</li>
						<li class="nav-item"  v-show="isLogin === true">
							<router-link to="/cart" class="nav-link">Giỏ hàng</router-link>
						</li>
						<li class="nav-item" v-show="isLogin === true">
							<div class="dropdown">
								<button class="btn btn-light dropdown-toggle" type="button" data-bs-toggle="dropdown"
									aria-expanded="false">
									{{ user?.username }}
								</button>
								<ul class="dropdown-menu">
									<li>
										<router-link to="/profile" class="dropdown-item">Thông tin tài khoản</router-link>
									</li>
									<li>
										<router-link to="/profile" class="dropdown-item">Lịch sử mua hàng</router-link>
									</li>
									<li>
										<router-link to="/cart" class="dropdown-item">Giỏ hàng</router-link>
									</li>
									<li>
										<router-link to="/cart" class="dropdown-item">Đổi mật khẩu</router-link>
									</li>
									<li><a class="dropdown-item" @click.prevent="logout()">Đăng xuất</a></li>
								</ul>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</nav>
	</div>
</template>
<script lang="ts">
import { defineComponent } from "vue";
import { User } from '@/core/model/user.model';
import { toast } from 'vue3-toastify';
export default defineComponent({
	name : 'NavComponent',
	data() {
		return {
			user: {} as User,
			isLogin: false as boolean
		}
	},methods: {
		logout() {
			localStorage.removeItem('access_token');
			localStorage.removeItem('user');
			this.$router.push('/login');
			toast.success('Đăng xuất thành công');
		}
	},
	mounted() {
		this.user = JSON.parse(localStorage.getItem('user') || '{}');
		this.isLogin = localStorage.getItem('access_token') ? true : false;
	}
});
</script>

<style scoped>
li {
	cursor: pointer;
}
</style>