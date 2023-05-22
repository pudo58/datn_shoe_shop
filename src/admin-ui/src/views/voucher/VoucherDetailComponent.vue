<template>
	<div class="h2" v-if="!isUpdate">Tạo mới voucher</div>
	<div class="h2" v-else>Cập nhật voucher</div>
	<hr>
	<form @submit.prevent="onSubmit()" class="form-control">
		<div class="form-floating mb-3">
			<input type="text" class="form-control" id="floatingPublisherName" v-model="voucher.name">
			<label for="floatingPublisherName">Tên voucher</label>
		</div>
		<div class="form-floating mb-3">
			<input class="form-control" type="date" v-model="voucher.effectFrom" id="floatingDesc">
			<label for="floatingDesc">Ngày bắt đầu</label>
		</div>
		<div class="form-floating mb-3">
			<input class="form-control" type="date" v-model="voucher.effectUntil" id="floatingAsc">
			<label for="floatingAsc">Ngày kết thúc</label>
		</div>
		<div class="form-floating mb-3">
			<input class="form-control" type="number" v-model="voucher.limitPerUser" id="limit">
			<label for="limit">Giới hạn cho mỗi người dùng</label>
		</div>
		<div class="form-floating mb-3" id="discountType">
			<select v-model="voucher.discountType" class="form-control">
				<option :value="Voucher.DISCOUNT_TYPE_PERCENT">Giảm theo phần trăm</option>
				<option :value="Voucher.DISCOUNT_TYPE_MONEY">Giảm theo số tiền</option>
			</select>
			<label for="discountType">Loại giảm giá</label>
		</div>
		<div class="form-floating mb-3">
			<input class="form-control" type="number" v-model="voucher.discountValue" id="discountValue">
			<label for="discountValue">Giá trị giảm giá</label>
		</div>
		<div class="form-floating mb-3">
			<input class="form-control" type="number" v-model="voucher.quantity" id="quantity">
			<label for="quantity">Số lượng</label>
		</div>
		<div class="form-floating mb-3">
			<select v-model="voucher.isAutoApply" id="isAuto" class="form-control">
				<option :value="true">Có </option>
				<option :value="false">Không</option>
			</select>
			<label for="isAuto">Voucher tự động</label>
		</div>
		<div class="form-floating mb-3">
			<input class="form-control" placeholder="mô tả" id="description"
			       v-model="voucher.description">
			<label for="description">Mô tả</label>
		</div>
		<button type="submit" class="btn btn-success d-flex justify-content-start">Lưu</button>
	</form>
	<div v-if="isUpdate" class="md-4">
		<hr>
		<div class="d-flex justify-content-between">
			<div class="h2">Voucher áp dụng cho sản phẩm</div>
			<div>
				<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalProduct">Gắn sản phẩm</button>
			</div>
		</div>
		<table class="table table-striped table-hover table-bordered">
			<thead>
			<tr>
				<th>STT</th>
				<th>Tên sản phẩm</th>
				<th>Giá</th>
				<th>Giảm giá</th>
				<th>Ngày tạo</th>
				<th>
					Hành động
				</th>
			</tr>
			</thead>
			<tbody>
			<tr v-for="(product,index) in productList">
				<td>{{index+1}}</td>
				<td>{{product?.name}}</td>
				<td>{{formatPrice(product?.price)}}</td>
				<td>{{product?.discount}}</td>
				<td>{{formatDate(product?.createdDate)}}</td>
				<td>
					<i class="bi bi-trash" role="button" @click.prevent="deleteById(index)"></i>
				</td>
			</tr>
			<tr v-if="productList.length === 0">
				<th colspan="6" >Không có sản phẩm nào</th>
			</tr>
			</tbody>
		</table>
	</div>
	<div v-if="isUpdate">
		<hr>
		<div class="d-flex justify-content-between">
			<div class="h2">Voucher áp dụng cho danh mục</div>
			<div>
				<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalCategory">Gắn danh mục</button>
			</div>
		</div>
		<table class="table table-striped table-hover table-bordered">
			<thead>
			<tr>
				<th>STT</th>
				<th>Tên danh mục</th>
				<th>
					Hành động
				</th>
			</tr>
			</thead>
			<tbody>
			<tr v-for="(category,index) in categoryList">
				<td>{{index+1}}</td>
				<td>{{category?.name}}</td>
				<td>
					<i class="bi bi-trash"></i>
				</td>
			</tr>
			<tr v-if="categoryList.length === 0">
				<th colspan="3">Không có danh mục nào</th>
			</tr>
			</tbody>
		</table>
	</div>

	<div v-if="isUpdate">
		<hr>
		<div class="d-flex justify-content-between">
			<div class="h2">User có voucher</div>
			<div>
				<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalUser">Gắn User</button>
			</div>
		</div>
		<table class="table table-striped table-hover table-bordered">
			<thead>
			<tr>
				<th>Username</th>
				<th>Họ tên</th>
				<th>SDT</th>
				<th>Địa chỉ</th>
				<th>Email</th>
				<th>
					Hành động
				</th>
			</tr>
			</thead>
			<tbody>
			<tr v-for="(user,index) in userList">
				<td>{{user?.username}}</td>
				<td>{{user?.fullName}}</td>
				<td>{{user?.phoneNumber}}</td>
				<td>{{user?.address}}</td>
				<td>{{user?.email}}</td>
				<td>
					<i class="bi bi-trash"></i>
				</td>
			</tr>
			<tr v-if="userList.length === 0">
				<th colspan="6">Không có user nào có voucher này</th>
			</tr>
			</tbody>
		</table>
	</div>
	<div class="modal fade" id="modalProduct" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg ">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<table class="table table-striped table-hover table-bordered">
						<thead>
						<tr>
							<th>
								<input type="checkbox" class="checkAllProduct">
							</th>
							<th>Tên sản phẩm</th>
							<th>Giá</th>
							<th>Giảm giá</th>
							<th>Ngày tạo</th>
						</tr>
						</thead>
						<tbody>
						<tr v-for="(product,index) in productListAll.content">
							<td>
								<input type="checkbox" :value="product.id" class="checkProduct" v-model="productIdListSelected" :disabled="disableProduct(product.id)">
							</td>
							<td>{{product?.name}}</td>
							<td>{{formatPrice(product?.price)}}</td>
							<td>{{product?.discount}}</td>
							<td>{{formatDate(product?.createdDate)}}</td>
						</tr>
						</tbody>
					</table>
					<ul class="pagination">
						<li class="page-item">
							<a class="page-link" @click.prevent="prevProduct()" :class="{'disabled' : pageProduct === 0}">Trước</a>
						</li>
						<li class="page-item" v-for="item in productListAll?.totalPages">
							<a class="page-link"  @click="pageProductChange(item)" role="button">{{ item }}</a>
						</li>
						<li class="page-item">
							<a role="button" class="page-link"  @click.prevent="nextProduct()"
							   :class="{'disabled' : pageProduct === productListAll.totalPages - 1}">Sau</a>
						</li>
					</ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" @click.prevent="addProductLink()">Thêm</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modalCategory" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="modalCatgeory">Modal title</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<table class="table table-striped table-hover table-bordered">
						<thead>
						<tr>
							<th>
								<input type="checkbox" class="checkAllProduct">
							</th>
							<th>Tên danh mục</th>
						</tr>
						</thead>
						<tbody>
						<tr v-for="(category,index) in categoryListAll.content">
							<td>
								<input type="checkbox" :value="category.id" class="checkProduct" v-model="categoryIdListSelected" :disabled="disableCategory(category.id)">
							</td>
							<td>{{category?.name}}</td>
						</tr>
						</tbody>
					</table>
					<ul class="pagination">
						<li class="page-item">
							<a class="page-link" @click.prevent="prevCategory()" :class="{'disabled' : pageCategory === 0}">Trước</a>
						</li>
						<li class="page-item" v-for="item in categoryListAll?.totalPages">
							<a class="page-link"  @click="pageCategoryChange(item)" role="button">{{ item }}</a>
						</li>
						<li class="page-item">
							<a role="button" class="page-link"  @click.prevent="nextCategory()"
							   :class="{'disabled' : pageCategory === categoryListAll.totalPages - 1}">Sau</a>
						</li>
					</ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" @click.prevent="addCategoryLink()">Thêm</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="modalUser" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="">Modal title</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<table class="table table-striped table-hover table-bordered">
						<thead>
						<tr>
							<th>
								<input type="checkbox" class="checkAllProduct">
							</th>
							<th>Username</th>
							<th>Họ tên</th>
							<th>SDT</th>
							<th>Địa chỉ</th>
							<th>Email</th>
						</tr>
						</thead>
						<tbody>
						<tr v-for="(user,index) in userListAll.content">
							<td>
								<input type="checkbox" :value="user.id" class="checkProduct" v-model="userIdListSelected" :disabled="disableUser(user.id)">
							</td>
							<td>{{user?.username}}</td>
							<td>{{user?.fullName}}</td>
							<td>{{user?.phoneNumber}}</td>
							<td>{{user?.address}}</td>
							<td>{{user?.email}}</td>
						</tr>
						</tbody>
					</table>
					<ul class="pagination">
						<li class="page-item">
							<a class="page-link" @click.prevent="prevUser()" :class="{'disabled' : pageUser === 0}">Trước</a>
						</li>
						<li class="page-item" v-for="item in userListAll?.totalPages">
							<a class="page-link"  @click="pageUserChange(item)" role="button">{{ item }}</a>
						</li>
						<li class="page-item">
							<a role="button" class="page-link"  @click.prevent="nextUser()"
							   :class="{'disabled' : pageUser === userListAll.totalPages - 1}">Sau</a>
						</li>
					</ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" @click.prevent="addUserLink()">Thêm</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {Voucher, VoucherLinkRequest} from "@/core/model/voucher.model";
import {VoucherService} from "@/core/service/voucher.service";
import {Product} from "@/core/model/product.model";
import {ProductService} from "@/core/service/product.service";
import {Category} from "@/core/model/category.model";
import {Pageable} from "@/core/model/core.base";
import {CategoryService} from "@/core/service/category.service";
import {UserService} from "@/core/service/user.service";
import {User} from "@/core/model/user.model";

export default defineComponent({
	name: "VoucherDetailComponent",
	data() {
		return {
			isUpdate: false as boolean,
			id : Number(this.$route.params.id as string),
			voucher : new Voucher(),
			voucherService : new VoucherService(),
			productService : new ProductService(),
			categoryService : new CategoryService(),
			userService : new UserService(),
			Voucher : Voucher,
			productList : [] as Product[],
			categoryList : [] as Category[],
			userList : [] as User[],
			productListAll : {} as Pageable<Product>,
			categoryListAll : {} as Pageable<Category>,
			userListAll : {} as Pageable<User>,
			productIdListSelected : [] as number[],
			categoryIdListSelected : [] as number[],
			userIdListSelected : [] as number[],
			pageProduct : 0 as number,
			pageCategory : 0 as number,
			pageUser : 0 as number,
		}
	},
	methods: {
		init() {
			if (this.id !== 0 && !isNaN(this.id)) {
				this.isUpdate = true;
				this.voucherService.findById(this.id).then((res) => {
					this.voucher = res;
				});
				this.voucherService.findCategoryByVoucherId(this.id).then((res) => {
					this.categoryList = res;
				});
				this.voucherService.findProductByVoucherId(this.id).then((res) => {
					this.productList = res;
				});
				this.voucherService.findUserByVoucherId(this.id).then((res) => {
					this.userList = res;
				});
			}
		},
		deleteById(id: number) {
			this.voucherService.delete(id).then(() => {

			});
		},
		formatDate(date: Date) {
			// format date to dd/mm/yyyy
			const d = new Date(date);
			let month = '' + (d.getMonth() + 1);
			let day = '' + d.getDate();
			const year = d.getFullYear();
			return [day, month, year].join('/');
		},
		formatPrice(price: number) {
			return new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(price);
		},
		onSubmit() {
			if (this.isUpdate) {
				this.voucherService.update(this.id,this.voucher).then(() => {
					this.$router.push('/admin/voucher');
				});
				return;
			} else {
				this.voucherService.save(this.voucher).then(() => {
					this.$router.push('/admin/voucher');
				});
			}
		},
		addProductLink(){
			const payload = {
				productIdList : this.productIdListSelected,
				voucherId : this.id
			}as VoucherLinkRequest;
			this.voucherService.addVoucherToProduct(payload).then(() => {
				this.init();
			});
		},
		initProduct(){
			return this.productService.findAll(this.pageCategory,10).then((res) =>{
				this.productListAll = res;
			});
		},
		prevProduct(){
			this.pageProduct--;
			this.initProduct();
		},
		nextProduct(){
			this.pageProduct++;
			this.initProduct();
		},
		pageProductChange(page : number){
			this.pageProduct = --page;
			this.initProduct();
		},
		disableProduct(id : number){
			if(this.productList){
				if(this.productList?.find((item) => item.id === id)){
					return true;
				}else{
					return false;
				}
			}
		},
		initCategory(){
			return this.categoryService.findAll(this.pageProduct,10).then((res) =>{
				this.categoryListAll = res;
			});
		},
		prevCategory(){
			this.pageCategory--;
			this.initProduct();
		},
		nextCategory(){
			this.pageCategory++;
			this.initProduct();
		},
		pageCategoryChange(page : number){
			this.pageCategory = --page;
			this.initProduct();
		},
		disableCategory(id : number){
			if(this.categoryList){
				if(this.categoryList?.find((item) => item.id === id)){
					return true;
				}else{
					return false;
				}
			}
		},
		addCategoryLink(){
			const payload = {
				categoryIdList : this.categoryIdListSelected,
				voucherId : this.id
			}as VoucherLinkRequest;
			this.voucherService.addVoucherToCategory(payload).then(() => {
				this.init();
			});
		},
		initUser(){
			return this.userService.getUser(this.pageUser,10).then((res) =>{
				this.userListAll = res;
			});
		},
		prevUser(){
			this.pageUser--;
			this.initUser();
		},
		nextUser(){
			this.pageUser++;
			this.initUser();
		},
		pageUserChange(page : number){
			this.pageUser = --page;
			this.initUser();
		},
		disableUser(id : number){
			if(this.userList){
				if(this.userList?.find((item) => item.id === id)){
					return true;
				}else{
					return false;
				}
			}
		},
		addUserLink(){
			const payload = {
				userIdList : this.userIdListSelected,
				voucherId : this.id
			}as VoucherLinkRequest;
			this.voucherService.addVoucherToUser(payload).then(() => {
				this.init();
			});
		},
	},
	created() {
		this.init();
		this.initProduct();
		this.initCategory();
		this.initUser();
	}
});
</script>