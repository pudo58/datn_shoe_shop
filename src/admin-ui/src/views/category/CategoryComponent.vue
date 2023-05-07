<template>
	<div class="row">
		<p class="h2">Quản lý danh mục</p>
	</div>
	<hr>
	<div class="row">
		<div class="input-group mb-3 col">
			<input type="text" class="form-control" placeholder="Mời bạn nhập tên danh mục " v-model="find">
			<button class="btn btn-outline-secondary" type="button" id="button-addon2" @click.prevent="findByName()">
				<i class="bi bi-search"></i>
				Tìm kiếm
			</button>
		</div>
		<div class="col"></div>
		<div class="col">
			<button class="btn btn-success m-1" @click.prevent="$router.push('/admin/category/new')">
				<i class="bi bi-pencil-fill"></i>
				Thêm mới
			</button>
			<button class="btn btn-success m-1 " data-bs-toggle="modal" data-bs-target="#exampleModal">
				<i class="bi bi-trash"></i>
				Thùng rác
			</button>
		</div>
	</div>
	<div class="table table-responsive" id="table">
		<table class="table table-bordered table-hover table-striped">
			<thead>
			<tr>
				<th>Tên danh mục</th>
				<th>Ngày tạo</th>
				<th>Mô tả</th>
				<th>Trạng thái</th>
				<th>Thao tác</th>
			</tr>
			</thead>
			<tbody>
			<tr class="align-middle" v-for="item in categoryList.content">
				<td>{{ item.name }}</td>
				<td>{{ dateTime(item.created + '') }}</td>
				<td>{{ item.description }}</td>
				<td>
					<span v-if="item.isTrash === false" class="badge bg-success">Hoạt động</span>
					<span v-else class="badge bg-danger">Khóa</span>
				</td>
				<td>
					<button class="btn btn-success btn-sm m-1" @click="$router.push('/admin/category/'+item.id)">
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
					<a class="page-link" @click.prevent="getCategory(page--,size)">Previous</a>
				</li>
				<li class="page-item" v-for="item in categoryList?.totalPages">
					<a class="page-link" href="#" @click="getCategory(item-1,size)">{{ item }}</a>
				</li>
				<li class="page-item">
					<a class="page-link" href="#" @click.prevent="getCategory(page++,size)">Next</a>
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
	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<table class="table table-bordered table-hover table-striped">
						<thead>
						<tr>
							<th>Tên danh mục</th>
							<th>Ngày tạo</th>
							<th>Mô tả</th>
							<th>Trạng thái</th>
							<th>Khôi phục</th>
						</tr>
						</thead>
						<tbody>
						<tr class="align-middle" v-for="item in categoryTrashList">
							<td>{{ item.name }}</td>
							<td>{{ dateTime(item.created + '') }}</td>
							<td>{{ item.description }}</td>
							<td>
								<span v-if="item.isTrash === false" class="badge bg-success">Hoạt động</span>
								<span v-else class="badge bg-danger">Khóa</span>
							</td>
							<td>
								<button class="btn btn-success btn-sm m-1" @click.prevent="restoreById(item.id)">
									<i class="bi bi-arrow-counterclockwise"></i>
								</button>
							</td>
						</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal" id="close">Close</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import {toast} from 'vue3-toastify';
import {Pageable} from "@/core/model/core.base";
import {Category} from "@/core/model/category.model";
import {CategoryService} from "@/core/service/category.service";
import moment from 'moment/moment';

export default defineComponent({
	name: "CategoryComponent",
	data() {
		return {
			categoryList: new Pageable<Category>(),
			loading: false,
			search: '' as string,
			categoryService: new CategoryService(),
			page: 0 as number,
			size: 10 as number,
			find: '' as string,
			categoryId: null,
			categoryTrashList : [] as Category[]
		}
	},
	methods: {
		getCategory(page: number, size: number) {
			this.categoryService.findAll(page, size).then(response => {
				this.categoryList = response;
			}).catch(error => {
				toast.error(error.message);
			})
		},
		getCategoryTrash() {
			this.categoryService.findAllTrash().then(response => {
				this.categoryTrashList = response;
			}).catch(error => {
				toast.error(error.message);
			})
		},
		dateTime(value: string) {
			if (value == null || value == undefined || value === '') {
				return '';
			} else {
				return moment(value).format('DD-MM-YYYY HH:mm:ss');
			}

		},
		onChange() {
			this.getCategory(this.page, this.size);
		},
		deleteById(id: number) {
			if (confirm("Bạn có chắc chắn muốn cho danh mục này vào thùng rác không?")) {
				this.categoryService.delete(id).then(data => {
					if (data === true) {
						toast.success("Cho danh mục này vào thùng rác thành công");
						this.getCategory(this.page, this.size);
						this.getCategoryTrash();
						document.getElementById('close')?.click();
					}
				}).catch(error => {
					toast.error(error.message);
				})
			}
		},
		findByName() {
			if (this.find === '' || this.find == null || this.find == undefined) {
				this.getCategory(this.page, this.size);
				return;
			}
			this.categoryService.findByName(this.find).then(response => {
				this.categoryList.content = [];
				this.categoryList.content.push(response);
			}).catch(error => {
				toast.error(error.message);
			})
		},
		restoreById(id: number) {
			if (confirm("Bạn có chắc chắn muốn khôi phục danh mục này không?")) {
				this.categoryService.restore(id).then(data => {
					if(data?.status == 200){
						toast.success(data?.message);
						this.getCategory(this.page, this.size);
						this.getCategoryTrash();
					}
				}).catch(error => {
					toast.error(error.message);
				})
			}
		}
	},
	created() {
		this.getCategory(this.page, this.size);
		this.getCategoryTrash();
	},
})
</script>

<style scoped>
#table {
	padding: 0px;
}
</style>