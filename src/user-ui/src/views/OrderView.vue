<template>
	<!-- Credit card form -->
	<section class="h-100">
		<div class="container h-100 py-5">
			<div class="row">
				<div class="col-md-8 mb-4">
					<div class="card mb-4">
						<div class="card-header py-3">
							<h5 class="mb-0">Thanh toán hóa đơn</h5>
						</div>
						<div class="card-body">
							<form @submit.prevent="doOrder()">
								<!-- 2 column grid layout with text inputs for the first and last names -->
								<div class="row mb-4">
									<div>
										<div class="form-floating">
											<input type="text" id="form6Example1" class="form-control" v-model="orderRequest.name"/>
											<label class="form-label" for="form6Example1">Họ tên
												<span class="text-danger">*</span>
											</label>
										</div>
									</div>
								</div>
								<!-- Text input -->
								<div class="form-floating mb-4">
									<input type="text" id="form6Example4" class="form-control" v-model="orderRequest.address"/>
									<label class="form-label" for="form6Example4">Địa chỉ
										<span class="text-danger">*</span>
									</label>
								</div>

								<!-- Email input -->
								<div class="form-floating mb-4">
									<input type="email" id="form6Example5" class="form-control" v-model="orderRequest.email"/>
									<label class="form-label" for="form6Example5">Email
										<span class="text-danger">*</span>
									</label>
								</div>

								<!-- Number input -->
								<div class="form-floating mb-4">
									<input type="number" id="form6Example6" class="form-control" v-model="orderRequest.phoneNumber"/>
									<label class="form-label" for="form6Example6">Số điện thoại
										<span class="text-danger">*</span>
									</label>
								</div>
								<hr class="my-4"/>

								<h5 class="mb-4">Hình thức thanh toán</h5>

								<div class="d-flex justify-content-start">
									<div class="form-check">
										<input class="form-check-input" type="radio" name="flexRadioDefault"
										       @change.prevent="isShowQRCode = true " id="checkoutForm3" :value="PaymentMethod.BANK_TRANSFER" v-model="orderRequest.paymentMethod"/>
										<label class="form-check-label" for="checkoutForm3">
											Chuyển khoản
										</label>
									</div>
									<div class="form-check">
										<input class="form-check-input" type="radio" name="flexRadioDefault" v-model="orderRequest.paymentMethod"
										       @change.prevent="isShowQRCode = false " id="checkoutForm4" :value="PaymentMethod.COD"/>
										<label class="form-check-label" for="checkoutForm4">
											Tiền mặt
										</label>
									</div>
								</div>
								<button class="btn btn-primary btn-lg btn-block" type="submit">
									Xác nhận thanh toán
								</button>
							</form>
						</div>
					</div>
				</div>

				<div class="col-md-4 mb-4">
					<div class="card mb-4">
						<div class="card-header py-3">
							<h5 class="mb-0">Chi tiết hóa đơn</h5>
						</div>
						<div class="card-body">
							<ul class="list-group list-group-flush">
								<li class="list-group-item d-flex justify-content-between align-items-center border-0 px-0 pb-0">
									Giá sản phẩm(bao gồm Giảm giá)
									<span>{{currency('VND',price)}}</span>
								</li>
								<li class="list-group-item d-flex justify-content-between align-items-center border-0 px-0 pb-0">
									Phí vận chuyển
									<span>{{currency('VND',feeShip)}}</span>
								</li>
								<li class="list-group-item d-flex justify-content-between align-items-center px-0">
									<small class="text-danger">Lưu ý : Đơn hàng trên 1.000.000 VND miễn phí vận chuyển</small>
								</li>
								<li class="list-group-item d-flex justify-content-between align-items-center border-0 px-0 mb-3">
									<div>
										<strong>Tổng tiền</strong>
									</div>
									<span><strong>{{ currency('VND',(feeShip + price)) }}</strong></span>
								</li>
							</ul>
						</div>
					</div>
					<div class="card mb-4" v-if="isShowQRCode">
						Mã QR Code thanh toán
						<img :src="qrCode">
					</div>
				</div>
			</div>
		</div>
	</section>
</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {Order, OrderRequest} from "@/core/model/order.model";
import {OrderService} from "@/core/service/order.service";
import {PaymentMethod} from "@/core/model/order.model";
import axios from "axios";
export default defineComponent({
	name: 'Checkout',
	data() {
		return {
			orderRequest : new OrderRequest(),
			orderService : new OrderService(),
			order : {} as Order,
			PaymentMethod: PaymentMethod,
			voucherId: 0,
			price : 0,
			feeShip : 0,
			qrCode : '',
			isShowQRCode : false
		}
	},
	methods: {
		doOrder() {
			let idString = this.$route.params.cartIdList as string;
			let ids = idString.split(',').map(Number);
			this.orderRequest.cartIdList = ids;
			this.orderRequest.voucherId = this.voucherId;
			this.orderRequest.userId = Number(localStorage.getItem('userId'));
			this.orderService.doOrder(this.orderRequest).then((res) => {
				this.$router.push("/order");
			});
		},
		async getPrice() {
			try {
				const idString = this.$route.params.cartIdList as string;
				const ids = idString?.split(',').map(Number);
				const payload = {
					cartIdList: ids,
					voucherId: this.voucherId
				} as OrderRequest;
				const res = await this.orderService.getPrice(payload);
				this.price = res.price;
				this.feeShip = res?.fee_ship;
			} catch (error) {
				console.error(error);
			}
		},
		async makeQRCode() {
			await this.getPrice();
			const api = "https://api.vietqr.io/v2/generate";
			const username = JSON.parse(localStorage.getItem("user") as string)?.username;
			const payload = {
				accountNo : "0704145768",
				accountName: "LA VAN THO",
				acqId: 970432,
				amount: this.price + this.feeShip,
				addInfo: username + "- Thanh toan don hang gia tri " + (this.price + this.feeShip) + " VND",
				format: "text",
				template: "compact2"
			};
			try {
				const response = await axios.post(api, payload);
				this.qrCode = response.data?.data.qrDataURL;
			} catch (error) {
				console.error(error);
			}
		},
		currency(currency: string, value: number) {
			return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: currency }).format(value);
		},
		loadData() {
			this.orderService.findFirstByUserId(Number(localStorage.getItem('userId'))).then((res) => {
				this.order = res;
				if(this.order){
					this.orderRequest.address = this.order.address;
					this.orderRequest.phoneNumber = this.order.phoneNumber;
					this.orderRequest.name = this.order.name;
					this.orderRequest.email = this.order.email;
					this.orderRequest.paymentMethod = this.order.paymentMethod;
				}
			});
		}
	},
	created() {
		this.getPrice();
		this.makeQRCode();
		this.loadData();
	}
})
</script>

<style scoped>

</style>