<template>
	<div class="container mt-5 mb-5">
		<div class="card">
			<div class="row g-0">
				<div class="col-md-6 border-end">
					<div class="d-flex flex-column justify-content-center">
						<div class="main_image">
							<img :src="'http://localhost/image/product/' + product.imageThumbnail"
							     width="450">
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="p-3 right-side">
						<div class="d-flex justify-content-between align-items-center"><h4>{{ product?.name }}</h4><span
							class="heart"><i
							class='bx bx-heart'></i></span></div>
						<div class="mt-2 pr-3 content"><p>
							{{ product.description }}
						</p></div>
						<div class="text-start">
							Giá bán: <span class="fw-bold"> {{
								formatMoney(product.price - (product.price * product.discount / 100))
							}}</span><br>
							Giá gốc: <span class="fw-bold"> {{ formatMoney(product.price) }}</span>
						</div>
						<div class="text-start">
							Giảm giá: <span class="fw-bold text-danger">{{ product.discount + '%' }}</span>
						</div>
						<!--						thuôc tính-->
						<div class="text-start">
							Danh mục : <span class="fw-bold">{{ product.category?.name }}</span>
						</div>
						<div class="text-start">
							Thương hiệu : <span class="fw-bold">{{ product.brand?.name }}</span>
						</div>
						<div class="text-start">
							Kiểu dáng : <span class="fw-bold">{{ product?.model }}</span>
						</div>
						<div class="text-start">
							Chất liệu : <span class="fw-bold">{{ product?.material }}</span>
						</div>
						<div v-if="product?.productDetailList?.length > 0" class="text-start">
							<span class="fw-bold">Kích cỡ</span>
							<div class="d-flex flex-row ">
								<button :class="{'bg-success text-light' : size == item.size.size}" type="button" class="m-1" v-for="item in product?.productDetailList" @click.prevent="size = item.size.size;getQuantity()">
									{{ item?.size?.size }}
								</button>
							</div>
						</div>
						<div v-if="product?.productDetailList?.length" class="text-start">
							<span class="fw-bold">Màu sắc</span>
							<div class="d-flex flex-row ">
							<button :class="{'bg-success text-light' : color == item.color.name}" class="m-1" v-for="item in product?.productDetailList" @click.prevent="color = item.color.name;getQuantity()">
								{{ item?.color?.name }}
							</button>
							</div>
						</div>
						<div class="text-start">
							Số lượng còn : <span class="fw-bold">{{ quantity}}</span>
						</div>
						<div class="text-start">
							Mô tả : <span
							class="fw-bold">{{ product.description == null ? 'Không có' : product.description }}</span>
						</div>
<!--						 chọn số lượng kiểu số-->
						<div class="text-start">
							<span>Chọn số lượng : </span>
							<div class="d-flex justify-content-start">
								<button class="btn btn-outline-dark" @click="userQuantity = userQuantity - 1" :disabled="userQuantity <= 0">-</button>
								<input type="text" class="form-control w-25" v-model="userQuantity" min="0" :max="quantity">
								<button class="btn btn-outline-dark" @click="userQuantity = userQuantity + 1" :disabled="userQuantity >= quantity">+</button>
							</div>

						</div>
						<div class="ratings d-flex flex-row align-items-center">
							<div class="d-flex flex-row"><i class='bx bxs-star'></i> <i class='bx bxs-star'></i> <i
								class='bx bxs-star'></i> <i class='bx bxs-star'></i> <i class='bx bx-star'></i></div>
							<span>441 reviews</span></div>
						<div class="buttons d-flex flex-row mt-5 gap-3">
							<button type="button" class="btn btn-dark" @click.prevent="addToCart()">Thêm vào giỏ hàng</button>
						</div>
						<div class="search-option"><i class='bx bx-search-alt-2 first-search'></i>
							<div class="inputs"><input type="text" name=""></div>
							<i class='bx bx-share-alt share'></i></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {ProductDetail} from "@/core/model/product-detail.model";
import {ProductResponse} from "@/core/model/product.model";
import {ProductService} from "@/core/service/product.service";
import {CartService} from "@/core/service/cart.service";
import {toast} from "vue3-toastify";
import {CardRequest} from "@/core/model/cart.model";
export default defineComponent({
	name: "ProductDetailView",
	data() {
		return {
			productId: this.$route.params.id as string,
			product: new ProductResponse(),
			productService: new ProductService(),
			productDetailList: new Array<ProductDetail>(),
			cartService: new CartService(),
			color : '',
			size : '',
			quantity : 0 as any,
			userQuantity : 0 as any,
			productDetailId : 0 as any
		}
	},
	methods: {
		getProduct() {
			this.productService.findById(Number.parseInt(this.productId)).then((response) => {
				this.product = response;
			}).catch((error) => {
				console.log(error);
			});
		},
		formatMoney(money: number) {
			return money?.toLocaleString('it-IT', {style: 'currency', currency: 'VND'});
		},
		getQuantity(){
			let flag = false;
			if(this.product?.productDetailList && this.product?.productDetailList.length > 0){
				this.product?.productDetailList?.forEach((item) => {
					if(item.color?.name == this.color && item.size?.size == this.size){
						flag = true;
						this.quantity = item.quantity;
						this.productDetailId = item.id;
						console.log(this.productDetailId)
					}
				});
				if(!flag){
					this.quantity = 0;
				}
			}
		},
		addToCart(){
			if(this.userQuantity > 0){
				const payload = {
					userId : Number(localStorage.getItem('userId')),
					productDetailId : this.productDetailId,
					quantity : this.userQuantity
				} as CardRequest;
				this.cartService.addToCart(payload).then(() => {
				}).catch((error) => {
				});
			}else{
				toast.error('Số lượng phải lớn hơn 0');
			}
		}
	},
	created() {
		this.getProduct();
	}
});
</script>

<style scoped>
body {
	background-color: #ecedee
}

.card {
	border: none;
	overflow: hidden
}

.thumbnail_images ul {
	list-style: none;
	justify-content: center;
	display: flex;
	align-items: center;
	margin-top: 10px
}

.thumbnail_images ul li {
	margin: 5px;
	padding: 10px;
	border: 2px solid #eee;
	cursor: pointer;
	transition: all 0.5s
}

.thumbnail_images ul li:hover {
	border: 2px solid #000
}

.main_image {
	display: flex;
	justify-content: center;
	align-items: center;
	border-bottom: 1px solid #eee;
	height: 400px;
	width: 100%;
	overflow: hidden
}

.heart {
	height: 29px;
	width: 29px;
	background-color: #eaeaea;
	border-radius: 50%;
	display: flex;
	justify-content: center;
	align-items: center
}

.content p {
	font-size: 12px
}

.ratings span {
	font-size: 14px;
	margin-left: 12px
}

.colors {
	margin-top: 5px
}

.colors ul {
	list-style: none;
	display: flex;
	padding-left: 0px
}

.colors ul li {
	height: 20px;
	width: 20px;
	display: flex;
	border-radius: 50%;
	margin-right: 10px;
	cursor: pointer
}

.colors ul li:nth-child(1) {
	background-color: #6c704d
}

.colors ul li:nth-child(2) {
	background-color: #96918b
}

.colors ul li:nth-child(3) {
	background-color: #68778e
}

.colors ul li:nth-child(4) {
	background-color: #263f55
}

.colors ul li:nth-child(5) {
	background-color: black
}

.right-side {
	position: relative
}

.search-option {
	position: absolute;
	background-color: #000;
	overflow: hidden;
	align-items: center;
	color: #fff;
	width: 200px;
	height: 200px;
	border-radius: 49% 51% 50% 50% / 68% 69% 31% 32%;
	left: 30%;
	bottom: -250px;
	transition: all 0.5s;
	cursor: pointer
}

.search-option .first-search {
	position: absolute;
	top: 20px;
	left: 90px;
	font-size: 20px;
	opacity: 1000
}

.search-option .inputs {
	opacity: 0;
	transition: all 0.5s ease;
	transition-delay: 0.5s;
	position: relative
}

.search-option .inputs input {
	position: absolute;
	top: 200px;
	left: 30px;
	padding-left: 20px;
	background-color: transparent;
	width: 300px;
	border: none;
	color: #fff;
	border-bottom: 1px solid #eee;
	transition: all 0.5s;
	z-index: 10
}

.search-option .inputs input:focus {
	box-shadow: none;
	outline: none;
	z-index: 10
}

.search-option:hover {
	border-radius: 0px;
	width: 100%;
	left: 0px
}

.search-option:hover .inputs {
	opacity: 1
}

.search-option:hover .first-search {
	left: 27px;
	top: 25px;
	font-size: 15px
}

.search-option:hover .inputs input {
	top: 20px
}

.search-option .share {
	position: absolute;
	right: 20px;
	top: 22px
}

.buttons .btn {
	height: 50px;
	width: 150px;
	border-radius: 0px !important
}
</style>