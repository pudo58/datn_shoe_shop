<template>
	<div class="row">
		<p class="h2">Quản lý đơn hàng</p>
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
</template>

<script lang="ts">
import {defineComponent} from "vue";
export default defineComponent({
	name: "OrderComponent",
	data () {
		return {

		}
	},
	methods : {

	},
	created() {

	}
});
</script>

<style scoped>

</style>