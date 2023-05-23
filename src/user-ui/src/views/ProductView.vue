<template>
	<div class="container-fluid mt-5 mb-5">
		<div v-show="isLoading" class="loading">
			<div class="spinner-border text-primary spinner-center" role="status">
			</div>
		</div>
		<!--	Searh input-->
		<div class="row d-flex justify-content-end">
			<div class="col-md-9">
				<div class="input-group mb-3">
					<input type="text" class="form-control" placeholder="Tìm kiếm sản phẩm"
					       aria-label="Recipient's username"
					       aria-describedby="button-addon2" v-model="productSearchRequest.keyword">
					<button title="Tìm kiếm" class="btn btn-outline-secondary" type="button" id="button-addon2"
					        @click.prevent="onChange()">
						<i class="bi bi-search"></i>
					</button>
				</div>
			</div>
		</div>
		<div class="row g-2">
			<div class="col-md-3">
				<div class="t-products p-2"><h6 class="text-uppercase">Bộ lọc</h6>
					<div class="heading d-flex justify-content-between align-items-center"><h6 class="text-uppercase">
						Danh mục</h6></div>
					<div class="p-lists">
						<div class="d-flex justify-content-between m-2" v-for="item in categoryList">
							<input type="checkbox" :value="item.id" :id="'label' + item.name" name="cate"
							       v-model="categoryIdListSelected" @change.prevent="onChange()">
							<label role="button" :for="'label' + item.name">{{ item.name }}</label>
							<span role="button" title="Tổng số sản phẩm trong danh mục">{{ item.totalProduct }}</span>
						</div>
					</div>
				</div>
				<div class="processor p-2">
					<div class="heading d-flex justify-content-between align-items-center"><h6 class="text-uppercase">
						Thuộc tính</h6></div>
					<div class="d-flex justify-content-between mt-2" v-for="(item,index) in attributeList">
						<div class="form-check">
							<input class="form-check-input" type="checkbox" :value="item.id" :id="item.name"
							       @change.prevent="onChange()" v-model="attributeIdListSelected">
							<label class="form-check-label" :for="item.name"> {{ item.name }} </label>
						</div>
					</div>
				</div>
				<div class="processor p-2">
					<div class="heading d-flex justify-content-between align-items-center"><h6 class="text-uppercase">
						Màu sắc</h6></div>
					<div class="d-flex justify-content-between mt-2" v-for="(item,index) in colorList">
						<div class="form-check">
							<input class="form-check-input" type="checkbox" :value="item.id" :id="item.name"
							       v-model="colorIdListSelected" @change.prevent="onChange()">
							<label class="form-check-label" :for="item.name"> {{ item.name }} </label>
						</div>
					</div>
				</div>
				<div class="processor p-2">
					<div class="heading d-flex justify-content-between align-items-center"><h6 class="text-uppercase">
						Giới tính</h6></div>
					<div class="d-flex justify-content-between mt-2">
						<div class="form-check">
							<input class="form-check-input" type="radio" :value="1"
							       v-model="productSearchRequest.gender" @change.prevent="onChange()" name="gender">
							<label class="form-check-label"> Nam </label>
								<br>
							<input class="form-check-input" type="radio" :value="0"
							       v-model="productSearchRequest.gender" @change.prevent="onChange()" name="gender">
							<label class="form-check-label"> Nữ </label>

						</div>
					</div>
				</div>
				<div class="brand p-2">
					<div class="heading d-flex justify-content-between align-items-center"><h6 class="text-uppercase">
						Hãng sản
						xuất</h6>
						</div>
					<div class="d-flex justify-content-between mt-2" v-for="item in publisherList">
						<div class="form-check">
							<input class="form-check-input" type="checkbox" :id="item.name" :value="item.id"
							       v-model="selectedIds" name="nsx" @change.prevent="onChange()">
							<label class="form-check-label" :for="item.name"> {{ item.name }} </label>
						</div>
						<span role="button" title="Tổng số sản phẩm của nhà sản xuất">{{ item.totalProduct }}</span>
					</div>
				</div>
			</div>
			<div class="col-md-9">
				<div class="row g-2">
					<div v-if="productList.content?.length === 0" class="text-dark h3"> Không có sản phẩm </div>
					<div class="col-md-4" v-for="item in productList.content">
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
				<div class="d-flex justify-content-between" v-if="productList.content?.length > 0">
					<nav aria-label="Page navigation example">
						<ul class="pagination">
							<li class="page-item">
								<a class="page-link" href="#" aria-label="Previous"
								   @click.prevent="getProductList(page--,size)" :class="{'disabled' : page == 0}">
									<span aria-hidden="true">&laquo;</span>
								</a>
							</li>
							<li class="page-item" v-for="item in productList.totalPages">
								<a class="page-link" href="#"
								   @click.prevent="getProductList(Number.parseInt(item) -1,15)">{{ item }}</a>
							</li>
							<li class="page-item">
								<a class="page-link" href="#" aria-label="Next"
								   @click.prevent="getProductList(page++,size)"  :class="{'disabled' : page == productList.totalPages - 1}">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</li>
						</ul>
					</nav>
					<div class="mt-4" v-if="productList.content?.length > 0">
						<span>Hiển thị</span>
						<select v-model="sort" @change="sortByCreatedDate">
							<option value="1">Hàng mới nhất</option>
							<option value="2">Hàng cũ nhất</option>
						</select>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {Pageable} from "@/core/model/core.base";
import {Product, ProductSearchRequest} from "@/core/model/product.model";
import {ProductService} from "@/core/service/product.service";
import {CategoryService} from "@/core/service/category.service";
import {CategoryResponse} from "@/core/model/category.model";
import {BrandService} from "@/core/service/brand.service";
import {BrandResponse} from "@/core/model/brand.model";
import {Attribute} from "@/core/model/attribute.model";
import {AttributeService} from "@/core/service/attribute.service";
import {Color} from "@/core/model/color.model";
import {ColorService} from "@/core/service/color.service";
import {io, Socket} from 'socket.io-client';

const socket: Socket = io('http://127.0.0.1:9092');

export default defineComponent({
	name: "Products",
	data() {
		return {
			productList: new Pageable<Product>(),
			categoryList: Array<CategoryResponse>(),
			categoryService: new CategoryService(),
			publisherService: new BrandService(),
			publisherList: Array<BrandResponse>(),
			productService: new ProductService(),
			colorList: Array<Color>(),
			colorService: new ColorService(),
			attributeList: Array<Attribute>(),
			attributeService: new AttributeService(),
			attributeIdListSelected: Array<number>(),
			productSearchRequest: new ProductSearchRequest(),
			selectedIds: Array<number>(),
			categoryIdListSelected: Array<number>(),
			colorIdListSelected: Array<number>(),
			sort: 1,
			page: 0,
			size: 15,
			isLoading: false as boolean
		}
	},
	methods: {
		getProductList() {
			this.isLoading = true;
			this.productService.findBySearch(this.productSearchRequest).then((res) => {
				this.productList = res;
				this.productList?.content?.map((item: Product) => {
					if (item.viewMore === null)
						item.viewMore = false;
				});
			});
			setTimeout(() => {
				this.isLoading = false;
			}, 1000);
		},
		getCategoryList() {
			this.categoryService.findAllCategoryData().then((res) => {
				this.categoryList = res;
			})
		},
		getPublisherList() {
			this.publisherService.findAllPublisherData().then((res) => {
				this.publisherList = res;
			})
		},
		getAttributeList() {
			this.attributeService.findAllAttribute().then((res) => {
				this.attributeList = res;
			})
		},
		getColorList() {
			this.colorService.findAll().then((res) => {
				this.colorList = res;
			})
		},
		sortByCreatedDate() {
			if (this.sort == 1) {
				this.productList.content?.sort((a: Product, b: Product) => {
					return new Date(b.createdDate || new Date()).getTime() - new Date(a.createdDate || new Date()).getTime();
				});
			} else {
				this.productList.content?.sort((a, b) => {
					return new Date(a.createdDate || new Date()).getTime() - new Date(b.createdDate || new Date()).getTime();
				});
			}
		},
		onChange() {
			this.productSearchRequest.brandIdList = this.selectedIds;
			this.productSearchRequest.categoryIdList = this.categoryIdListSelected;
			this.productSearchRequest.colorIdList = this.colorIdListSelected;
			this.productSearchRequest.attributeIdList = this.attributeIdListSelected;
			this.getProductList();
		},
		redirectDetail(id: number) {
			this.$router.push({name: 'ProductDetail', params: {id: id}});
		},
		listenEvent() {
			//check connect socket
			console.log(socket.connected)
			socket.on('connect', () => {
				console.log('connect');
			});
			//send event
			socket.emit('client-send', 'hello server');
			//receive event
			socket.on('product', (data: any) => {
				console.log(data);
			});

		}
	},
	created() {
		this.getProductList();
		this.getCategoryList();
		this.getPublisherList();
		this.getAttributeList();
		this.getColorList();
		this.listenEvent();
	}
})
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