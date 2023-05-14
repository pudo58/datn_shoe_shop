<template>
	<div>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1>Chi tiết đơn hàng</h1>
				</div>
			</div>
			<div class="table-responsive">
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
					<tr>
						<td>
							1
						</td>
						<td>
							{{ orderPage?.order?.code }}
						</td>
						<td>
							{{ orderPage?.order?.address }}
						</td>
						<td>
							{{ orderPage?.order?.phoneNumber }}
						</td>
						<td>
							{{ orderPage?.order?.name }}
						</td>
						<td>
							{{ orderPage?.order?.email }}
						</td>
						<td>
							{{ orderPage?.order?.created }}
						</td>
						<td>
							{{ orderPage?.order?.note }}
						</td>
						<td>
							{{ orderPage?.order?.paymentMethod }}
						</td>
						<td>
							<span class="badge"
							      :class="[{'bg-warning': orderPage?.order?.status === Order.PENDING},{'bg-success': orderPage?.order?.status === Order.CONFIRMED || orderPage?.order?.status === Order.DELIVERED || orderPage?.order?.status === Order.DELIVERING || orderPage?.order?.status === Order.RECEIVED},{'bg-danger': orderPage?.order?.status === Order.CANCELLED},
							      {'bg-danger' : orderPage?.order?.status === Order.RETURNED }]">
								{{ Order.getStatus(orderPage?.order?.status) }}
							</span>
						</td>
					</tr>
					</tbody>
				</table>

				<div class="mb-lg-4">
					<h3>Sản phẩm</h3>
					<table class="table table-bordered table-hover table-striped">
						<thead>
						<tr>
							<th>STT</th>
							<th>Tên sản phẩm</th>
							<th>Số lượng</th>
							<th>Thành tiền</th>
						</tr>
						</thead>
						<tbody>
						<tr v-for="(item, index) in orderPage?.order?.orderDetails" :key="index">
							<td>
								{{ index + 1 }}
							</td>
							<td>
								{{ item?.productDetail?.product?.name }}
							</td>
							<td>
								{{ item?.quantity }}
							</td>
							<td>
								{{ item?.price }}
							</td>
						</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {OrderService} from "@/core/service/order.service";
import {Order} from "@/core/model/order.model";

export default defineComponent({
	name: "OrderDetailComponent",
	data() {
		return {
			orderPage: {} as any,
			id: Number(this.$route.params.id as string),
			orderService: new OrderService(),
			Order: Order
		}
	},
	methods: {
		initOrder() {
			this.orderService.detail(this.id).then(res => {
				this.orderPage = res;
			}).catch(err => {
				console.log(err);
			})
		}
	},
	created() {
		this.initOrder();
	}
});
</script>

<style>

</style>