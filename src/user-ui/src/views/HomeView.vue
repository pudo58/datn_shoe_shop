<template>
	<div class="container row mb-5">
		<p class="h1">Top 10 sản phẩm mới nhất</p>
		<div v-if="productList?.length === 0" class="text-dark h3"> Không có sản phẩm </div>
		<div class="col-md-4" v-for="item in productList">
			<div class="product py-4"><span class="off bg-success" v-if="item?.discount">{{ item.discount }} % OFF</span>
				<div class="text-center"><img class="image"
				                              :src="'http://localhost/image/product/'+item.imageThumbnail">
				</div>
				<div :title="item.name" class="about" role="button"><h5>
					{{ !item.viewMore && item.name.length > 50 ? item.name.substring(0, 50) + "... " : item.name }}<small
					v-if="item.name.length > 50" @click.prevent="item.viewMore = !item.viewMore"
					title="xem thêm" class="text-info" role="button">Xem thêm</small></h5><span>{{
						item.price.toLocaleString('it-IT', {style: 'currency', currency: 'VND'})
					}} VND</span>
				</div>
				<div :title="'Chi tiết sản phẩm ' + item.name"
				     class="cart-button mt-3 px-2 d-flex justify-content-between align-items-center">
					<button class="btn btn-primary text-uppercase" @click.prevent="redirectDetail(item.id)">
						Chi tiết
					</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {Product} from "@/core/model/product.model";
import {ProductService} from "@/core/service/product.service";
export default defineComponent({
	name: "HomeView",
	data() {
		return {
			productList: [] as Product[],
			productService: new ProductService()
		}
	},
	methods: {
		redirectDetail(id: number) {
			this.$router.push({name: 'ProductDetail', params: {id: id}})
		},
		initData() {
			this.productService.findTop10().then(res => {
				this.productList = res
			})
		}
	},
	created() {
		this.initData();
	}
});
</script>

<style scoped>
body {
	background-color: #eeeeee;
}

.processor {
	background-color: #fff;
	margin-top: 5px;
	border-bottom: 1px solid #eee
}

.brand {
	background-color: #fff;
	border-bottom: 1px solid #eee
}

.type {
	background-color: #fff
}

.product {
	padding: 10px;
	background-color: #fff;
	border-radius: 5px;
	position: relative;
	border: 1px solid gray;
}

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

.about span {
	color: #5629c0;
	font-size: 16px
}

.cart-button button {
	font-size: 12px;
	color: #fff;
	background-color: #5629c0;
	height: 38px
}

.cart-button button:focus,
button:active {
	font-size: 12px;
	color: #fff;
	background-color: #5629c0;
	box-shadow: none
}

.product_fav:hover i {
	color: #fff
}

.about {
	margin-top: 12px
}

.off {
	position: absolute;
	left: 65%;
	top: 6%;
	width: 80px;
	text-align: center;
	height: 30px;
	line-height: 8px;
	border-radius: 5px;
	font-size: 13px;
	display: flex;
	align-items: center;
	justify-content: center;
	color: #fff
}

.image {
	width: 100%;
	height: 200px;
	object-fit: cover;
}
</style>