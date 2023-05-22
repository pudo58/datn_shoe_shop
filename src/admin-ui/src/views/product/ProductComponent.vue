<template>
	<div class="row">
		<p class="h2">Quản lý sản phẩm</p>
	</div>
	<hr>
	<div class="row">
		<div class="input-group mb-3 col">
			<input type="text" class="form-control" placeholder="Mời bạn nhập tên sản phẩm" v-model="name">
			<button class="btn btn-outline-secondary" type="button" id="button-addon2" @click.prevent="findByName()">
				<i class="bi bi-search"></i>
				Tìm kiếm
			</button>
		</div>
		<div class="col"></div>
		<div class="col">
			<button class="btn btn-success m-1"
			        @click.prevent="$router.push('/admin/product/new')">
				<i class="bi bi-pencil-fill"></i>
				Thêm mới
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
			<tr v-if="!!productList.content?.length > 0" class="align-middle"
			    v-for="(item,index) in productList.content">
				<td class="fw-bold">{{ '#' + (index + 1) }}</td>
				<td class="fw-bold link-primary text-start" role="button">{{ item.name }}</td>
				<td>
					<img :src="'http://localhost/image/product/' + item.imageThumbnail" alt="" width="100px">
				</td>
				<td class="fw-bold">{{ formatMoney(item.price) }}</td>
				<td class="fw-bold">{{ item.discount + '%' }}</td>
				<td>{{ item.category.name }}</td>
				<td>{{ item.description === null ? '' : item.description.substring(0, 6).concat('...') }}</td>
				<td>{{ item.brand.name }}</td>
				<td>
					<span v-if="item.status === 1" class="badge bg-success" role="button">Hoạt động</span>
					<span v-if="item.status === 2 " class="badge bg-danger" role="button">Hết hàng</span>
				</td>
				<td>
					<button class="btn btn-success btn-sm m-1"
					        @click.prevent="$router.push('/admin/product/' + item.id)">
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
					<a class="page-link" @click.prevent="findAll(page--,size)"
					   :class="{disabled : disablePrev}" role="button">Trước</a>
				</li>
				<li class="page-item" v-for="item in productList?.totalPages">
					<a class="page-link"  @click="findAll(item-1,size)" role="button">{{ item }}</a>
				</li>
				<li class="page-item">
					<a role="button" class="page-link" @click.prevent="findAll(page++,size)"
					   :class="{disabled : disableNext}">Sau</a>
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
</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {Product} from "@/core/model/product.model";
import {Pageable} from "@/core/model/core.base";
import {ProductService} from "@/core/service/product.service";

export default defineComponent({
	name: "ProductComponent",
	data() {
		return {
			productList: new Pageable<Product>(),
			productService: new ProductService(),
			page: 0 as number,
			size: 10 as number,
			name: "" as string,
			isUpdate: false as boolean,
			disablePrev: false as boolean,
			disableNext: false as boolean,
			columns: [
				'STT',
				'Tên sản phẩm',
				'Hình ảnh',
				'Giá',
				'Giảm giá',
				'Danh mục',
				'Mô tả',
				'Hãng sản xuất',
				'Trạng thái',
				'Chức năng'
			]
		}
	},
	methods: {
		findAll(page: number, size: number) {
			this.productService.findAll(page, size).then((res) => {
				this.productList = res;
				this.disablePrev = page === 0 ? true : false;
				if (this.productList.last) {
					this.disableNext = true;
				} else {
					this.disableNext = false;
				}
			});
		},
		formatMoney(value: number) {
			return value.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
		},
		onChange() {
			this.findAll(this.page, this.size);
		},
		deleteById(id: number) {
			this.productService.delete(id).then((res) => {
				this.productList.content?.splice(this.productList.content?.findIndex((item) => item.id === id), 1);
			})
		},
		findByName() {
			this.productService.findByName(this.name).then((res) => {
				if(this.productList){
					this.productList.content = res;
				}
			})
		}
	},
	created() {
		this.findAll(this.page, this.size);
	}
})
</script>

<style scoped>

img {
	width: 80px;
	height: 60px;
}
</style>