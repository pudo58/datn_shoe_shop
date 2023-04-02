<template>
	<div class="row">
		<p class="h2">Quản lý sản phẩm</p>
	</div>
	<hr>
	<div class="row">
		<div class="input-group mb-3 col">
			<input type="text" class="form-control" placeholder="Mời bạn nhập tên hãng sản xuất" v-model="name">
			<button class="btn btn-outline-secondary" type="button" id="button-addon2" @click.prevent="findByName()">
				<i class="bi bi-search"></i>
				Tìm kiếm
			</button>
		</div>
		<div class="col"></div>
		<div class="col">
			<button class="btn btn-success m-1"
			        @click.prevent="$refs.productDetail.openModal();isUpdate=false;product = {}">
				<i class="bi bi-pencil-fill"></i>
				Thêm mới
			</button>
			<button class="btn btn-danger m-1">
				<i class="bi bi-trash-fill"></i>
				Xóa
			</button>
		</div>
	</div>
	<div class="table table-responsive" id="table">
		<table class="table table-bordered table-hover table-striped">
			<thead>
			<tr>
				<th v-for="item in columns">{{ item }}</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td colspan="11" v-if="!!productList.content?.length == 0">
					<strong>Không có dữ liệu</strong>
				</td>
			</tr>
			<tr v-if="!!productList.content?.length > 0" class="align-middle" v-for="(item,index) in productList.content">
				<td class="fw-bold">{{ index + 1 }}</td>
				<td class="fw-bold link-primary" role="button">{{ item.name }}</td>
				<td>
					<img :src="item.imageThumbnail" alt="" width="80px" height="80px">
				</td>
				<td class="fw-bold">{{ formatMoney(item.price) }}</td>
				<td class="fw-bold">{{ item.discount + '%' }}</td>
				<td class="fw-bold">{{ formatMoney(item.price * (item.discount / 100)) }}</td>
				<td>{{ item.category.name }}</td>
				<td>{{ item.description }}</td>
				<td>{{ item.publisher.name }}</td>
				<td>
					<span class="badge bg-success" role="button">Chi tiết</span>
				</td>
				<td>
					<button class="btn btn-success btn-sm m-1"
					        @click.prevent="$refs.productDetail.openModal();product = item;">
						<i class="bi bi-pencil-fill"></i>
					</button>
					<button class="btn btn-danger btn-sm m-1" @click.prevent="deleteById(item.id)">
						<i class="bi bi-trash-fill"></i>
					</button>
				</td>
			</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
		<nav class="col">
			<ul class="pagination">
				<li class="page-item">
					<a class="page-link" @click.prevent="findAll(page--,size)">Trước</a>
				</li>
				<li class="page-item" v-for="item in productList?.totalPages">
					<a class="page-link" href="#" @click="findAll(item-1,size)">{{ item }}</a>
				</li>
				<li class="page-item">
					<a class="page-link" href="#" @click.prevent="findAll(page++,size)">Sau</a>
				</li>
			</ul>
			<div class="p-2 col pagination">
				Số phần tử
				<select v-model="size" @change.prevent="onChange()">
					<option :value="10">10</option>
					<option :value="25">25</option>
					<option :value="50">50</option>
				</select>
			</div>
		</nav>
	</div>
	<product-detail-action-component ref="productDetail"></product-detail-action-component>
</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {Product} from "@/core/model/product.model";
import {Pageable} from "@/core/model/core.base";
import {ProductService} from "@/core/service/product.service";
import ProductDetailActionComponent from "@/views/admin/component/product/ProductDetailActionComponent.vue";
export default defineComponent({
	name: "ProductComponent",
	components: {
		ProductDetailActionComponent
	},
	data() {
		return {
			product: new Product(),
			productList: new Pageable<Product>(),
			productService: new ProductService(),
			page: 0 as number,
			size: 10 as number,
			name: "" as string,
			isUpdate: false as boolean,
			columns: [
				'STT',
				'Tên sản phẩm',
				'Hình ảnh',
				'Giá',
				'Giảm giá',
				'Giá sau khi giảm',
				'Mô tả',
				'Danh mục',
				'Nhà sản xuất',
				'Chi tiết',
				'Chức năng'
			]
		}
	},
	methods: {
		findAll(page: number, size: number) {
			this.productService.findAll(page, size).then((res) => {
				this.productList = res;
			})
		},
		formatMoney(value: number) {
			return value.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
		},
		onChange() {
			this.findAll(this.page, this.size);
		},
		deleteById(id: number) {
			this.productService.delete(id).then((res) => {
				this.findAll(this.page, this.size);
			})
		},
	},
	created() {
		this.findAll(this.page, this.size);
	}
})
</script>

<style scoped>
</style>