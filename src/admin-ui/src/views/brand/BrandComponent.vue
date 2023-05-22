<template>
	<div class="row">
		<p class="h2">Quản lý hãng sản xuất</p>
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
			<button class="btn btn-success m-1" @click="this.$router?.push('/admin/brand/new')">
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
				<th>Tên hãng</th>
				<th>Link website</th>
				<th>Thao tác</th>
			</tr>
			</thead>
			<tbody>
			<tr class="align-middle" v-for="item in brandList.content">
				<td>{{ item.name }}</td>
				<td>
					<a :href="item.website" target="_blank">{{ item.website }}</a>
				</td>
				<td>
					<button class="btn btn-success btn-sm m-1"
					        @click.prevent="$router.push('/admin/brand/'+item.id)">
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
					<a class="page-link" @click.prevent="getBrand(page--,size)" :class="{'disabled' : page == 0}">Trước</a>
				</li>
				<li class="page-item" v-for="item in brandList?.totalPages">
					<a class="page-link" href="#" @click="getBrand(item-1,size)">{{ item }}</a>
				</li>
				<li class="page-item">
					<a class="page-link" href="#" @click.prevent="getBrand(page++,size)" :class="{'disabled' : page == brandList?.totalPages - 1}">Sau</a>
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
import {Brand} from "@/core/model/brand.model";
import {Pageable} from "@/core/model/core.base";
import {BrandService} from "@/core/service/brand.service";
import BrandDetailComponent from "@/views/brand/BrandDetailComponent.vue";
import {toast} from "vue3-toastify";

export default defineComponent({
	name: 'BrandComponent',
	components: {
		BrandDetailComponent,
	},
	data() {
		return {
			brandList: new Pageable<Brand>(),
			brandService: new BrandService(),
			page: 0,
			size: 10,
			name: '' as string,
		}
	},
	methods: {
		async getBrand(page: number, size: number) {
			const res = await this.brandService.findAll(page, size) as Pageable<Brand>;
			this.brandList = res;
		},
		deleteById(id: number) {
			if (confirm("Bạn có chắc chắn muốn xóa?")) {
				this.brandService.delete(id).then((res) => {
					if (res) {
						toast.success("Xóa thành công");
						this.getBrand(this.page, this.size);
					}
				})
			}
		},
		onChange() {
			this.getBrand(this.page, this.size);
		},
		findByName() {
			if (this.name == null || this.name == '')
				 this.getBrand(this.page, this.size);
			this.brandService.findByName(this.name).then((res) => {
				console.log(this.brandList.content)
				if (res) {
					this.brandList.content = [];
					this.brandList.content?.push(res);
				}
			});

		}
	},
	created() {
		this.getBrand(this.page, this.size);
	}
})
</script>