<template>
	<div class="row">
		<p class="text-center h2">
			Báo cáo đơn hàng
		</p>
	</div>
	<div class="container">
		<form class="form-control mb-3" @submit.prevent="findAllOrderStatistical()">
			<div class="row d-flex justify-content-start mb-3">
				<label class="form-label col-sm-4">Chọn trạng thái</label>
				<select class="form-control-sm col-sm-8" v-model="model.status">
					<option v-for="item in ORDER_STATUS_LIST" :value="item.value">{{item.label}}</option>
				</select>
			</div>
			<div class="row d-flex justify-content-start mb-3">
				<label class="form-label col-sm-4">Chọn khoảng ngày</label>
				<input type="date" class="form-control-sm col-sm-4" v-model="model.fromDate">
				<input type="date" class="form-control-sm col-sm-4" v-model="model.toDate">
			</div>
			<div class="form-label">
				<button class="btn btn-outline-dark">Thống kê</button>
			</div>
		</form>
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
				</tr>
				</tbody>
			</table>
		</div>
	</div>
	<nav aria-label="Page navigation example">
		<ul class="pagination">
			<li class="page-item"><a class="page-link" href="#" @click.prevent="prePage()">Previous</a></li>
			<li class="page-item" v-for="(item,index) in orderPage.totalPages">
				<a class="page-link" href="#" @click.prevent="clickPage(item-1)">{{item}}</a>
			</li>
			<li class="page-item"><a class="page-link" href="#" @click.prevent="nextPage()">Next</a></li>
		</ul>
	</nav>
</template>

<style scoped>

</style>

<script lang="ts">
import {defineComponent} from "vue";
import {Order, ORDER_STATUS_LIST, OrderStatisticalDto} from "@/core/model/order.model";
import {BrandService} from "@/core/service/brand.service";
import {CategoryService} from "@/core/service/category.service";
import {OrderService} from "@/core/service/order.service";
import {Pageable} from "@/core/model/core.base";
import {toast} from "vue3-toastify"
export default defineComponent({
	name: "ChatComponent",
	data() {
		return {
			ORDER_STATUS_LIST: ORDER_STATUS_LIST,
			brandIdList: [],
			categoryIdList: [],
			brandService: new BrandService(),
			orderPage : new Pageable<Order>(),
			categoryService : new CategoryService(),
			orderService: new OrderService(),
			model : new OrderStatisticalDto(),
			Order: Order,
			page: 0,
			size: 10,
		}
	},
	methods: {
		initBrandIdList(){
			this.brandService.findAllBrand().then((res) => {
				this.brandIdList = res;
			}).catch((err) => {
				console.log(err);
			})
		},
		initCategoryIdList(){
			this.categoryService.findAllCategory().then((res) => {
				this.categoryIdList = res;
			}).catch((err) => {
				console.log(err);
			})
		},
		findAllOrderStatistical(){
			this.model.page = this.page;
			this.model.size = this.size;
			this.orderService.findAllByStatus(this.model).then((res) => {
				this.orderPage = res;
				if(res.status == 500){
					toast.warning(res.message);
				}
			}).catch((err) => {
				console.log(err);
			})
		},
		dateTimeFormat(date: Date) {
			return new Date(date).toLocaleString();
		},
		prePage(){
			if(this.page > 0){
				this.page--;
				this.findAllOrderStatistical();
			}
		},
		nextPage(){
			if(this.page < this.orderPage.totalPages - 1){
				this.page++;
				this.findAllOrderStatistical();
			}
		},
		clickPage(page: number){
			this.page = page;
			this.findAllOrderStatistical();
		}
	},
	created() {
		this.initBrandIdList();
		this.initCategoryIdList();
		this.findAllOrderStatistical();
	}
});
</script>