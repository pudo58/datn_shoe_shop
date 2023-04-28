<template>
	<nav class="navbar navbar-expand-lg  bg-secondary fixed-top">
		<div class="container">
			<router-link to="/" class="navbar-brand">Aboo Shop</router-link>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
			        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse " id="navbarNav">
				<ul class="navbar-nav me-auto ">
					<li class="nav-item">
						<router-link to="/" class="nav-link">Trang chủ</router-link>
					</li>
					<li class="nav-item">
						<router-link to="/product" class="nav-link">Sản phẩm</router-link>
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
						<router-link to="/login" class="nav-link" title="Đăng nhập">Đăng nhập</router-link>
					</li>
					<li class="nav-item" v-show="!isLogin">
						<router-link to="/register" class="nav-link" title="Đăng ký">Đăng ký</router-link>
					</li>
					<li class="nav-item" v-show="isLogin === true" title="Giỏ hàng">
						<router-link to="/cart" class="nav-link">
							<i class="bi bi-cart-fill"></i>
							Giỏ hàng
						</router-link>
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
									<router-link to="/history" class="dropdown-item">Lịch sử mua hàng</router-link>
								</li>
								<li>
									<router-link to="/cart" class="dropdown-item">
										Giỏ hàng
									</router-link>
								</li>
								<li><a class="dropdown-item" @click.prevent="logout()">Đăng xuất</a></li>
							</ul>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</nav>
</template>
<script lang="ts">
import {defineComponent} from "vue";
import {User} from '@/core/model/user.model';
import {toast} from 'vue3-toastify';

export default defineComponent({
	name: 'NavComponent',
	data() {
		return {
			user: {} as User,
			isLogin: false as boolean
		}
	}, methods: {
		logout() {
			localStorage.removeItem('access_token');
			localStorage.removeItem('user');
			this.$router.push('/login');
			toast.success('Đăng xuất thành công');
		}
	},
	created() {
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