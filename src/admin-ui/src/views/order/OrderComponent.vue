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
	</div>
	<div class="table table-responsive" id="table">
		<table class="table table-bordered table-hover table-striped">
			<thead>
			<tr>
				<th>STT</th>
				<th>Mã đơn hàng</th>
				<th>Địa chỉ</th>
				<th>SĐT</th>
				<th>Họ tên</th>
				<th>Email</th>
				<th>Ngày tạo</th>
				<th>Ghi chú</th>
				<th>Hình thức thanh toán</th>
				<th>Trạng thái</th>
				<th>Chi tiết</th>
				<th>Thao tác</th>
			</tr>
			</thead>
			<tbody>
			<tr class="align-middle" v-for="item in orderPage.content">
				<td>{{ item.id }}</td>
				<td>{{ item.code }}</td>
				<td>{{ item.address }}</td>
				<td>{{ item.phoneNumber }}</td>
				<td>{{ item.name }}</td>
				<td>{{ item.email }}</td>
				<td>{{ dateTimeFormat(item.created) }}</td>
				<td>{{ item.note }}</td>
				<td>{{ item.paymentMethod }}</td>
				<td>
				<span class="badge"
				      :class="[{'bg-warning': item.status === Order.PENDING},{'bg-success': item.status === Order.CONFIRMED || item.status === Order.DELIVERED || item.status === Order.DELIVERING || item.status === Order.RECEIVED},{'bg-danger': item.status === Order.CANCELLED},
					      {'bg-danger' : item.status === Order.RETURNED }]">
						{{ Order.getStatus(item.status) }}
					</span>
				</td>
				<td>
					<button class="btn btn-warning" title="Chi tiết" @click.prevent="this.$router.push('/admin/order/'+item.id)">
						<i class="bi bi-eye-fill"></i>
					</button>
				</td>
				<td>
					<button v-if="item.status === Order.PENDING" title="Duyệt đơn hàng" class="btn btn-warning"
					        @click.prevent="approve(item.id)">
						<i class="bi bi-pencil-fill"></i>
					</button>
					<button v-if="item.status === Order.CONFIRMED" title="Giao hàng" class="btn btn-warning"
					        @click.prevent="delivery(item.id)">
						<i class="bi bi-truck"></i>
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
					<a class="page-link" @click.prevent="prePage()" :class="{'disabled' : page == 0}">Trước</a>
				</li>
				<li class="page-item" v-for="item in orderPage?.totalPages">
					<a class="page-link"  @click="changePage(item)">{{ item }}</a>
				</li>
				<li class="page-item">
					<a class="page-link" @click.prevent="nextPage()"  :class="{'disabled' : page == orderPage?.totalPages - 1}">Sau</a>
				</li>
			</ul>
			<div class="p-2 col pagination">
				Số phần tử
				<select v-model="size" @change.prevent="findAll()">
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
import {Pageable} from "@/core/model/core.base";
import {Order} from "@/core/model/order.model";
import {OrderService} from "@/core/service/order.service";

export default defineComponent({
	name: "OrderComponent",
	data() {
		return {
			orderPage: new Pageable<Order>(),
			orderService: new OrderService(),
			page: 0,
			size: 10,
			Order: Order,
		}
	},
	methods: {
		findAll() {
			this.orderService.findAll(this.page, this.size).then(async res => {
				this.orderPage = await res;
			})
		},
		approve(id: number) {
			this.orderService.approve(id).then(res => {
				this.findAll();
			})
		},
		dateTimeFormat(date: Date) {
			return new Date(date).toLocaleString();
		},
		delivery(id: number) {
			this.orderService.delivery(id).then(res => {
				this.findAll();
			})
		},
		prePage() {
			if (this.page > 0) {
				this.page--;
				this.findAll();
			}
		},
		nextPage() {
			if(this.orderPage && this.orderPage.totalPages){
				if (this.page < this.orderPage?.totalPages - 1) {
					this.page++;
					this.findAll();
				}
			}
		},
		changePage(page: number) {
			this.page = --page;
			this.findAll();
		},
	},
	created() {
		this.findAll();
	}
});
</script>

<style scoped>

</style>