<template>
	<div class="container-fluid mt-5 mb-5">
		<div class="row g-2">
			<div class="col-md-3">
				<div class="t-products p-2"><h6 class="text-uppercase">Bộ lọc</h6>
					<div class="heading d-flex justify-content-between align-items-center"><h6 class="text-uppercase">
						Danh mục</h6> <span>--</span></div>
					<div class="p-lists">
						<div class="d-flex justify-content-between m-2" v-for="item in categoryList">
							<input type="radio" :value="item.id" :id="'label' + item.name" name="cate" v-model="productSearchRequest.categoryId" @change.prevent="onChange">
							<label role="button" :for="'label' + item.name">{{ item.name }}</label>
							<span role="button" title="Tổng số sản phẩm trong danh mục">{{ item.totalProduct }}</span>
						</div>
					</div>
				</div>
				<div class="processor p-2">
					<div class="heading d-flex justify-content-between align-items-center"><h6 class="text-uppercase">
						Thuộc tính</h6> <span>--</span></div>
					<div class="d-flex justify-content-between mt-2" v-for="(item,index) in attributeList" >
						<div class="form-check">
							<input class="form-check-input" type="checkbox" :value="item.id" :id="item.name" @change.prevent="checked();onChange">
							<label class="form-check-label" :for="item.name"> {{ item.name }} </label>
						</div>
					</div>
				</div>
				<div class="brand p-2">
					<div class="heading d-flex justify-content-between align-items-center"><h6 class="text-uppercase">
						Hãng sản
						xuất</h6>
						<span>--</span></div>
					<div class="d-flex justify-content-between mt-2" v-for="item in publisherList">
						<div class="form-check">
							<input class="form-check-input" type="radio" :id="item.name" :value="item.id" v-model="productSearchRequest.publisherId" name="nsx" @change.prevent="onChange">
							<label class="form-check-label" :for="item.name"> {{ item.name }} </label>
						</div>
						<span role="button" title="Tổng số sản phẩm của nhà sản xuất">{{ item.totalProduct }}</span>
					</div>
				</div>
				<div class="type p-2 mb-2">
					<div class="heading d-flex justify-content-between align-items-center"><h6 class="text-uppercase">
						Type</h6>
						<span>--</span></div>
					<div class="d-flex justify-content-between mt-2">
						<div class="form-check"><input class="form-check-input" type="checkbox" value=""
						                               id="flexCheckDefault">
							<label class="form-check-label" for="flexCheckDefault"> Hybrid </label></div>
						<span>23</span></div>
					<div class="d-flex justify-content-between mt-2">
						<div class="form-check"><input class="form-check-input" type="checkbox" value=""
						                               id="flexCheckChecked11"
						                               checked> <label class="form-check-label"
						                                               for="flexCheckChecked11">
							Laptop </label></div>
						<span>24</span></div>
					<div class="d-flex justify-content-between mt-2">
						<div class="form-check"><input class="form-check-input" type="checkbox" value=""
						                               id="flexCheckChecked12"
						                               checked> <label class="form-check-label"
						                                               for="flexCheckChecked12">
							Desktop </label></div>
						<span>14</span></div>
					<div class="d-flex justify-content-between mt-2">
						<div class="form-check"><input class="form-check-input" type="checkbox" value=""
						                               id="flexCheckChecked13"
						                               checked> <label class="form-check-label"
						                                               for="flexCheckChecked13">
							Touch </label></div>
						<span>28</span></div>
					<div class="d-flex justify-content-between mt-2">
						<div class="form-check"><input class="form-check-input" type="checkbox" value=""
						                               id="flexCheckChecked14"
						                               checked> <label class="form-check-label"
						                                               for="flexCheckChecked14">
							Tablets </label></div>
						<span>44</span></div>
				</div>
			</div>
			<div class="col-md-9">
				<div class="row g-2">
					<div class="col-md-4" v-for="item in productList.content">
						<div class="product py-4"><span class="off bg-success" v-if="item?.discount">{{ item.discount }} % OFF</span>
							<div class="text-center"><img class="image"
							                              :src="'http://localhost/image/product/'+item.imageThumbnail">
							</div>
							<div class="about text-center"><h5>{{ item.name }}</h5> <span>{{ item.price }} VND</span>
							</div>
							<div class="cart-button mt-3 px-2 d-flex justify-content-between align-items-center">
								<button class="btn btn-primary text-uppercase" @click.prevent="redirectDetail(item.id)">Chi tiết</button>
								<div class="add"><span class="product_fav"><i class="fa fa-heart-o"></i></span> <span
									class="product_fav"><i class="fa fa-opencart"></i></span></div>
							</div>
						</div>
					</div>
				</div>
				<div class="d-flex justify-content-between">
					<nav aria-label="Page navigation example">
						<ul class="pagination">
							<li class="page-item">
								<a class="page-link" href="#" aria-label="Previous" @click.prevent="getProductList(page--,size)">
									<span aria-hidden="true">&laquo;</span>
								</a>
							</li>
							<li class="page-item" v-for="item in productList.totalPages">
								<a class="page-link" href="#" @click.prevent="getProductList(Number.parseInt(item) -1,15)">{{item}}</a>
							</li>
							<li class="page-item">
								<a class="page-link" href="#" aria-label="Next" @click.prevent="getProductList(page++,size)">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</li>
						</ul>
					</nav>
					<div class="mt-4">
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
import {PublisherService} from "@/core/service/publisher.service";
import {PublisherResponse} from "@/core/model/publisher.model";
import {Attribute} from "@/core/model/attribute.model";
import {AttributeService} from "@/core/service/attribute.service";

export default defineComponent({
	name: "Products",
	data() {
		return {
			productList: new Pageable<Product>(),
			categoryList: Array<CategoryResponse>(),
			categoryService: new CategoryService(),
			publisherService: new PublisherService(),
			publisherList: Array<PublisherResponse>(),
			productService: new ProductService(),
			attributeList: Array<Attribute>(),
			attributeService: new AttributeService(),
			attributeIdListSelected: Array<number>(),
			productSearchRequest : new ProductSearchRequest(),
			sort: 1,
			page: 0,
			size: 15
		}
	},
	methods: {
		getProductList(page,size) {
			this.productService.findAll(page, size).then((res) => {
				this.productList = res;
			})
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
		sortByCreatedDate() {
			if(this.sort == 1){
				this.productList.content.sort((a, b) => {
					return new Date(b.createdDate).getTime() - new Date(a.createdDate).getTime();
				});
			}else{
				this.productList.content.sort((a, b) => {
					return new Date(a.createdDate).getTime() - new Date(b.createdDate).getTime();
				});
			}
		},
		onChange(){
			this.productService.findBySearch(this.productSearchRequest).then((res) => {
				this.productList.content = res;
				console.log(res);
			})
		},
		checked(){
			// Lọc ra các giá trị được chọn từ mảng attributeIdList
			this.selectedIds = this.productSearchRequest.attributeIdList.filter(value => value !== null);
		},
		redirectDetail(id: number) {
			this.$router.push({name: 'ProductDetail', params: {id: id}});
		}
	},
	created() {
		this.getProductList(this.page,this.size);
		this.getCategoryList();
		this.getPublisherList();
		this.getAttributeList();
		this.productSearchRequest.name = "";
		this.productSearchRequest.attributeIdList = [];
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

.product_fav i {
	line-height: 40px;
	color: #5629c0;
	font-size: 15px
}

.product_fav {
	display: inline-block;
	width: 36px;
	height: 39px;
	background: #FFFFFF;
	box-shadow: 0px 1px 5px rgba(0, 0, 0, 0.1);
	border-radius: 11%;
	text-align: center;
	cursor: pointer;
	margin-left: 3px;
	-webkit-transition: all 200ms ease;
	-moz-transition: all 200ms ease;
	-ms-transition: all 200ms ease;
	-o-transition: all 200ms ease;
	transition: all 200ms ease
}

.product_fav:hover {
	background: #5629c0
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