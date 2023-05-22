<template>
	<div class="row">
		<div class="h2">Chi tiết danh mục</div>
		<hr>
		<form @submit.prevent="addCategory()">
			<div class="form-floating mb-3">
				<input type="text" class="form-control" id="floatingCategoryName" v-model="category.name">
				<label for="floatingCategoryName">Tên danh mục</label>
			</div>
			<div class="form-floating mb-3">
              <textarea class="form-control" placeholder="Mô tả ...." id="floatingDesc"
                        v-model="category.description"></textarea>
				<label for="floatingDesc">Mô tả</label>
			</div>
			<br>
			<button type="submit" class="btn btn-success d-flex float-start">Lưu</button>
		</form>
	</div>
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="row mt-3">
						<hr>
						<h1 class="modal-title fs-5" id="staticBackdropLabel">Danh sách thuộc tính</h1>
						<div class="modal-body">
							<div class="table-responsive">
								<table class="table table-bordered">
									<thead>
									<tr>
										<th>
											<input type="checkbox" class="form-check-input" id="checkAll"
											       @change.prevent="checkAll()">
										</th>
										<th>STT</th>
										<th>Tên thuộc tính</th>
										<th>Kiểu dữ liệu</th>
									</tr>
									</thead>
									<tbody>
									<tr v-for="(item,index) in attributeList?.content" :key="index"
									    v-hide="isExistAttribute(item.id)">
										<td>
											<input type="checkbox" :value="item.id" class="form-check-input" id="check"
											       v-model="attributeIdList"
											       :disabled="isExistAttribute(item.id)">
										</td>
										<td>{{ index + 1 }}</td>
										<td>{{ item.name }}</td>
										<td>{{ item.type }}</td>
									</tr>
									</tbody>
								</table>
							</div>
							<div class="row">
								<nav class="col">
									<ul class="pagination">
										<li class="page-item">
											<a class="page-link" @click.prevent="getAttribute(page--,size)">Previous</a>
										</li>
										<li class="page-item" v-for="item in attributeList?.totalPages">
											<a class="page-link" href="#" @click="getAttribute(item-1,size)">{{
													item
												}}</a>
										</li>
										<li class="page-item">
											<a class="page-link" href="#" @click.prevent="getAttribute(page++,size)">Next</a>
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
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" @click.prevent="addAll">Thêm mới</button>
				</div>
			</div>
		</div>
	</div>
	<hr>
	<div v-if="this.id !== 0 && !isNaN(this.id)">
		<div class="d-flex justify-content-between p-1">
			<div class="text-center h3">Thuộc tính của danh mục <b>{{ category.name }}</b></div>
			<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
				Thêm thuộc tính
			</button>
		</div>
		<div>
			<div class="table-responsive">
				<table class="table table-striped table-hover table-bordered">
					<thead>
					<tr>
						<th>STT</th>
						<th>Tên thuộc tính</th>
						<th>Kiểu dữ liệu</th>
						<th>
							Thao tác
						</th>
					</tr>
					</thead>
					<tbody>
					<tr v-for="(item,index) in attributeCurrentList" :key="index">
						<td>{{ index + 1 }}</td>
						<td>{{ item.name }}</td>
						<td>{{ item.type }}</td>
						<td>
							<i role="button" @click.prevent="deleteAttributeRequest(item.id)"
							   class="bi bi-trash"></i>
						</td>
					</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {CategoryService} from "@/core/service/category.service";
import {Category} from "@/core/model/category.model";
import {toast} from 'vue3-toastify';
import {AttributeService} from "@/core/service/attribute.service";
import {Pageable} from "@/core/model/core.base";
import {Attribute, AttributeAddAllRequest, DeleteAttributeRequest} from "@/core/model/attribute.model";

export default defineComponent({
	name: 'CategoryDetailComponent',
	data() {
		return {
			categoryService: new CategoryService(),
			category: new Category(),
			id: 0 as number,
			attributeService: new AttributeService(),
			attributeList: new Pageable<Attribute>(),
			page: 0,
			size: 10,
			attributeIdList: [] as number[],
			attributeRequest: new AttributeAddAllRequest(),
			attributeCurrentList: [] as Attribute[],
			deleteAttribute: new DeleteAttributeRequest(),
		}
	},
	methods: {
		addCategory() {
			if (this.id !== 0 && !isNaN(this.id)) {
				this.categoryService.update(this.category).then((res) => {
					this.$router.push('/admin/category');
					toast.success("Cập nhật thành công");
					return;
				});
			} else {
				this.categoryService.save(this.category).then((res) => {
					this.$router.push('/admin/category');
				});
			}
		},
		getAttribute(page: number, size: number) {
			this.attributeService.findAll(page, size).then(response => {
				this.attributeList = response;
			}).catch(error => {
				toast.error(error.message);
			})
		},
		onChange() {
			this.getAttribute(this.page, this.size);
		},
		openModal() {
			document.getElementById('show-modal-attr')?.click();
		},
		checkAll() {
			let checkAll = document.getElementById('checkAll') as HTMLInputElement;
			let check = document.getElementsByClassName('form-check-input') as HTMLCollectionOf<HTMLInputElement>;
			if (checkAll.checked) {
				for (let i = 0; i < check.length; i++) {
					if (!check[i].disabled) {
						check[i].checked = true;
						// Cập nhật giá trị của attributeIdList
						if (!this.attributeIdList.includes(Number.parseInt(check[i].value))) {
							this.attributeIdList.push(Number.parseInt(check[i].value));
						}
					}
				}
			} else {
				for (let i = 0; i < check.length; i++) {
					check[i].checked = false;
					// Cập nhật giá trị của attributeIdList
					this.attributeIdList = this.attributeIdList.filter(id => id !== Number.parseInt(check[i].value));
				}
			}
		},
		addAll() {
			this.attributeRequest.categoryId = this.category.id || 0;
			this.attributeRequest.attributeIdList = this.attributeIdList;
			this.attributeService.addAll(this.attributeRequest).then(response => {
				this.findAttributeByCategoryId(this.id);
			}).catch(error => {
				toast.error(error.message);
			});
		},
		findAttributeByCategoryId(categoryId: number) {
			this.attributeService.findAllByCategoryId(this.id).then(response => {
				this.attributeCurrentList = response;
			}).catch(error => {
				toast.error(error.message);
			})
		},
		deleteAttributeRequest(attributeId: number) {
			if (confirm("Bạn có chắc chắn muốn xóa thuộc tính này?")) {
				this.deleteAttribute.categoryId = this.id;
				this.deleteAttribute.attributeId = attributeId;
				this.attributeService.deleteAttribute(this.deleteAttribute).then(response => {
					this.findAttributeByCategoryId(this.id);
				}).catch(error => {
					toast.error(error.message);
				})
			}
		},
		isExistAttribute(attributeId: number) {
			let check = false;
			this.attributeCurrentList.forEach(item => {
				if (item.id == attributeId) {
					check = true;
				}
			})
			return check;
		}
	},
	created() {
		this.id = Number.parseInt(this.$route.params.id as string);
		if (this.id !== 0 && !isNaN(this.id)) {
			this.categoryService.findById(this.id).then((res) => {
				this.category = res;
			});
			this.getAttribute(this.page, this.size);
			this.findAttributeByCategoryId(this.id);
		}
	}
})
</script>