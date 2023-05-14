<template>
	<div class="container table-responsive mt-lg-5">
		<table v-if="orderPage?.content?.length > 0" class="table table-striped table-hover table-bordered">
			<thead>
			<tr>
				<th>STT</th>
				<th>Mã đơn hàng</th>
				<th>Địa chỉ</th>
				<th>SĐT</th>
				<th>Họ tên</th>
				<th>Email</th>
				<th>Ngày tạo</th>
				<th>Hình thức thanh toán</th>
				<th>Trạng thái</th>
				<th>Hành động</th>
			</tr>
			</thead>
			<tbody>
			<tr v-for="item in orderPage.content">
				<td>{{ item.id }}</td>
				<td>{{ item.code }}</td>
				<td>{{ item.address }}</td>
				<td>{{ item.phoneNumber }}</td>
				<td>{{ item.name }}</td>
				<td>{{ item.email }}</td>
				<td>{{ dateTimeFormat(item.created) }}</td>
				<td>{{ item.paymentMethod }}</td>
				<td>
					<span class="badge"
					      :class="[{'bg-warning': item.status === Order.PENDING},{'bg-success': item.status === Order.CONFIRMED || item.status === Order.DELIVERED || item.status === Order.DELIVERING || item.status === Order.RECEIVED},{'bg-danger': item.status === Order.CANCELLED},
					      {'bg-danger' : item.status === Order.RETURNED }]">
						{{ Order.getStatusLabel(item.status) }}
					</span>
				</td>
				<td>
					<button v-if="item.status === Order.CONFIRMED" title="Yêu cầu hoàn hàng" class="btn btn-warning"
					        data-bs-toggle="modal" data-bs-target="#exampleModal" @click.prevent="idCurrent  = item.id">
						<i class="bi bi-arrow-counterclockwise"></i>
					</button>
					<button v-if="item.status === Order.PENDING" title="Hủy đơn hàng" class="btn btn-warning"
					        @click.prevent="cancel(item.id)">
						<i class="bi bi-archive-fill"></i>
					</button>
					<button v-if="item.status === Order.DELIVERING" title="Đã nhận được hàng" class="btn btn-warning"
					        @click.prevent="receivedOrder(item.id)">
						<i class="bi bi-bag-check"></i>
					</button>
				</td>
			</tr>
			</tbody>
			<tr>
			</tr>
		</table>
	</div>
	<div class="container mt-lg-5">
		<div class="row">
			<div class="col-md-12">
				<div class="d-flex justify-content-between">
					<select @change.prevent="findAll()" v-model="size">
						<option value="10">10</option>
						<option value="20">20</option>
						<option value="50">50</option>
					</select>
					<div class="d-flex">
						<button class="btn btn-primary" @click.prevent="prePage()">
							<i class="bi bi-arrow-left"></i>
						</button>
						<div v-for=" item in orderPage.totalPages">
							<button class="btn btn-primary" @click.prevent="changePage(item - 1)">
								{{ item }}
							</button>
						</div>
						<button class="btn btn-primary" @click.prevent="nextPage()">
							<i class="bi bi-arrow-right"></i>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">Lý do trả hàng</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<textarea class="w-100" placeholder="Lý do trả hàng" v-model="note">
					</textarea>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
					<button class="btn btn-primary" @click.prevent="returnOrder(idCurrent)">Xác nhận</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {Pageable} from "@/core/model/core.base";
import {Order} from "@/core/model/order.model";
import {OrderService} from "@/core/service/order.service";
import {toast} from "vue3-toastify"

export default defineComponent({
	name: "OrderListView",
	data() {
		return {
			orderPage: new Pageable<Order>(),
			orderService: new OrderService(),
			userId: Number(localStorage.getItem("userId")) || 0,
			idCurrent: 0,
			page: 0,
			size: 10,
			Order : Order,
			note : "" as string
		}
	},
	methods: {
		findAll() {
			if (this.userId != 0) {
				this.orderService.getOrderByUserId(this.userId, this.page, this.size).then(async res => {
					this.orderPage = await res;
				});
			}
		},
		dateTimeFormat(date: Date) {
			return new Date(date).toLocaleString();
		},
		prePage() {
			if (this.page > 0) {
				this.page--;
				this.findAll();
			}
		},
		nextPage() {
			if(this.orderPage.totalPages){
				if (this.page < this.orderPage.totalPages - 1) {
					this.page++;
					this.findAll();
				}
			}
		},
		changePage(page: number) {
			this.page = page;
			this.findAll();
		},
		cancel(id: number) {
			if(confirm("Bạn có chắc chắn muốn hủy đơn hàng này?")){
				this.orderService.cancelOrder(id).then(res => {
					if (res) {
						toast.success("Hủy đơn hàng thành công!");
						this.findAll();
					}
				});
			}
		},
		returnOrder(id: number) {
			if(confirm("Bạn có chắc chắn muốn trả lại đơn hàng này?")){
				this.orderService.returnOrder(id,this.note).then(res => {
					if (res) {
						this.findAll();
					}
				});
			}
		},
		receivedOrder(id: number) {
			if(confirm("Bạn có chắc chắn muốn xác nhận đã nhận hàng?")){
				this.orderService.receiveOrder(id).then(res => {
					if (res) {
						this.findAll();
					}
				});
			}
		}
	},
	created() {
		this.findAll();
	}
});
</script>

<style scoped>

</style>