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
			<button class="btn btn-success m-1">
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
			<tr class="align-middle" v-for="item in publisherList.content">
				<td>{{ item.name }}</td>
				<td>
					<a :href="item.website" target="_blank">{{ item.website }}</a>
				</td>
				<td>
					<button class="btn btn-success btn-sm m-1"
					        @click.prevent="$router.push('/admin/publisher/'+item.id)">
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
					<a class="page-link" @click.prevent="getPublisher(page--,size)">Previous</a>
				</li>
				<li class="page-item" v-for="item in publisherList?.totalPages">
					<a class="page-link" href="#" @click="getPublisher(item-1,size)">{{ item }}</a>
				</li>
				<li class="page-item">
					<a class="page-link" href="#" @click.prevent="getPublisher(page++,size)">Next</a>
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
import {Publisher} from "@/core/model/publisher.model";
import {Pageable} from "@/core/model/core.base";
import {PublisherService} from "@/core/service/publisher.service";
import PublisherDetailComponent from "@/views/publisher/PublisherDetailComponent.vue";
import {toast} from "vue3-toastify";

export default defineComponent({
	name: 'PublisherComponent',
	components: {
		PublisherDetailComponent,
	},
	data() {
		return {
			publisherList: new Pageable<Publisher>(),
			publisherService: new PublisherService(),
			page: 0,
			size: 10,
			name: '' as string,
		}
	},
	methods: {
		async getPublisher(page: number, size: number) {
			const res = await this.publisherService.findAll(page, size) as Pageable<Publisher>;
			this.publisherList = res;
		},
		deleteById(id: number) {
			if (confirm("Bạn có chắc chắn muốn xóa?")) {
				this.publisherService.delete(id).then((res) => {
					if (res) {
						toast.success("Xóa thành công");
						this.getPublisher(this.page, this.size);
					}
				})
			}
		},
		getEmit(publisher: Publisher) {
			this.publisherList.content?.push(publisher);
		},
		onChange() {
			this.getPublisher(this.page, this.size);
		},
		async findByName() {
			if (this.name == null || this.name == '')
				await this.getPublisher(this.page, this.size);
			const res = await this.publisherService.findByName(this.name);
			if (res) {
				this.publisherList.content?.push(res);
			}
		}
	},
	created() {
		this.getPublisher(this.page, this.size);
	}
})
</script>