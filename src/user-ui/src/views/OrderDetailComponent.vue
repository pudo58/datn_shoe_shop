<template>
	<div>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<br><br><br>
					<h1>Chi tiết đơn hàng</h1>
				</div>
			</div>
			<div class="mb-lg-4">
				<h3 align="left">Sản phẩm</h3>
				<table class="table table-bordered table-hover table-striped">
					<thead>
					<tr>
						<th>STT</th>
						<th>Tên sản phẩm</th>
						<th>Số lượng</th>
						<th>Giới tính</th>
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
							{{Product.getGender( item?.productDetail?.product?.gender )}}
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
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {OrderService} from "@/core/service/order.service";
import {Order} from "@/core/model/order.model";
import {Product} from "@/core/model/product.model";

export default defineComponent({
	name: "OrderDetailComponent",
	data() {
		return {
			orderPage: {} as any,
			id: Number(this.$route.params.id as string),
			orderService: new OrderService(),
			Order: Order,
			Product : Product
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