<template>
	<section class="h-100" style="background-color: #eee;">
		<div class="container h-100 py-5">
			<div class="row d-flex justify-content-center align-items-center h-100 mb-3">
				<div class="col-10">
					<div class="d-flex justify-content-between align-items-center">
						<h3 class="fw-normal mt-4 text-black">Giỏ hàng</h3>
					</div>
					<div v-if="cartPage.content.length > 0" class="card rounded-3 mb-4" v-for="item in cartPage.content">
						<div class="card-body p-4">
							<div class="row d-flex justify-content-between align-items-center">
								<div class="col-md-2 col-lg-2 col-xl-2">
									<img
										:src="'http://localhost/image/product/' + item.productDetail.product.imageThumbnail"
										class="img-fluid rounded-3" :alt="item.productDetail.product.name">
								</div>
								<div class="col-md-3 col-lg-3 col-xl-3">
									<p class="lead fw-normal mb-2">{{item.productDetail.product.name}}</p>
									<p><span class="text-muted">Size: </span>{{item.productDetail.size.size}}<span class="text-muted">Color: </span>{{item.productDetail.color.name}}</p>
								</div>
								<div class="col-md-3 col-lg-3 col-xl-2 d-flex">
									<button class="btn btn-link px-2">
										<i class="bi bi-dash"></i>
									</button>

									<input min="0" name="quantity" :value="item.quantity" type="number"
									       class="form-control form-control-sm" />

									<button class="btn btn-link px-2">
										<i class="bi bi-plus"></i>
									</button>
								</div>
								<div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
									<h5 title="Giá" class="mb-0">Giá : <span class="fw-bold">{{ + item.productDetail.product.price}}</span></h5>
								</div>
								<div class="col-md-1 col-lg-1 col-xl-1 text-end">
									<a role="button" title="Xóa" class="text-danger" @click.prevent="deleteCart(item.id)"><i class="bi bi-trash"></i></a>
								</div>
							</div>
						</div>
					</div>
					<div class="card"  v-if="cartPage.content.length > 0">
						<div class="card-body">
							<button type="button" class="btn btn-warning btn-block btn-lg" @click.prevent="$router.push('/cart/checkout')">Thanh toán</button>
						</div>
					</div>
					<div class="card"  v-if="cartPage.content.length == 0">
						<div class="card-body">
							<p class="lead fw-normal mb-2">Giỏ hàng trống</p>
						</div>
					</div>

				</div>
			</div>
		</div>
	</section>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import {Pageable} from "@/core/model/core.base";
import {CartService} from "@/core/service/cart.service";
import {CardRequest, Cart} from "@/core/model/cart.model";
export default defineComponent({
	name: 'Cart',
	data() {
		return {
			cartPage : new Pageable<Cart>(),
			cartService: new CartService()
		}
	},
	methods: {
		findAllCartByUserId() {
			const payload = {
				userId : localStorage.getItem('userId')
			} as CardRequest
			this.cartService.getCartByUserId(payload).then(response => {
				this.cartPage = response;
			})
		},
		deleteCart(id: number) {
			if(confirm('Bạn có chắc chắn muốn xóa sản phẩm này không ?')) {
				this.cartService.deleteById(id).then(response => {
					this.cartPage.content = this.cartPage.content.filter(item => item.id != id);
				})
			}
		}
	},
	created() {
		this.findAllCartByUserId();
	}
})
</script>

<style scoped>
</style>
