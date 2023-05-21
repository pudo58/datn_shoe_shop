<template>
	<div class="h2" v-if="!isUpdate">Tạo mới voucher</div>
	<div class="h2" v-else>Cập nhật voucher</div>
	<hr>
	<form @submit.prevent="onSubmit()" class="form-control">
		<div class="form-floating mb-3">
			<input type="text" class="form-control" id="floatingPublisherName" v-model="voucher.name">
			<label for="floatingPublisherName">Tên voucher</label>
		</div>
		<div class="form-floating mb-3">
			<input class="form-control" type="date" v-model="voucher.effectFrom" id="floatingDesc">
			<label for="floatingDesc">Ngày bắt đầu</label>
		</div>
		<div class="form-floating mb-3">
			<input class="form-control" type="date" v-model="voucher.effectUntil" id="floatingAsc">
			<label for="floatingAsc">Ngày kết thúc</label>
		</div>
		<div class="form-floating mb-3">
			<input class="form-control" type="number" v-model="voucher.limitPerUser" id="limit">
			<label for="limit">Giới hạn cho mỗi người dùng</label>
		</div>
		<div class="form-floating mb-3" id="discountType">
			<select v-model="voucher.discountType" class="form-control">
				<option :value="Voucher.DISCOUNT_TYPE_PERCENT">Giảm theo phần trăm</option>
				<option :value="Voucher.DISCOUNT_TYPE_MONEY">Giảm theo số tiền</option>
			</select>
			<label for="discountType">Loại giảm giá</label>
		</div>
		<div class="form-floating mb-3">
			<input class="form-control" type="number" v-model="voucher.discountValue" id="discountValue">
			<label for="discountValue">Giá trị giảm giá</label>
		</div>
		<div class="form-floating mb-3">
			<input class="form-control" type="number" v-model="voucher.quantity" id="quantity">
			<label for="quantity">Số lượng</label>
		</div>
		<div class="form-floating mb-3">
			<select v-model="voucher.isAutoApply" id="isAuto" class="form-control">
				<option :value="true">Có </option>
				<option :value="false">Không</option>
			</select>
			<label for="isAuto">Voucher tự động</label>
		</div>
		<div class="form-floating mb-3">
			<input class="form-control" placeholder="mô tả" id="description"
			       v-model="voucher.description">
			<label for="description">Mô tả</label>
		</div>
		<button type="submit" class="btn btn-success d-flex justify-content-start">Lưu</button>
	</form>
	<hr>
	<div class="h2">Sản phẩm thuộc voucher</div>
	<table>

	</table>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {Voucher} from "@/core/model/voucher.model";
import {VoucherService} from "@/core/service/voucher.service";
export default defineComponent({
	name: "VoucherDetailComponent",
	data() {
		return {
			isUpdate: false as boolean,
			id : Number(this.$route.params.id as string),
			voucher : new Voucher(),
			voucherService : new VoucherService(),
			Voucher : Voucher
		}
	},
	methods: {
		init() {
			if (this.id !== 0 && !isNaN(this.id)) {
				this.isUpdate = true;
				this.voucherService.findById(this.id).then((res) => {
					this.voucher = res;
				});
			}
		},
		onSubmit() {
			if (this.isUpdate) {
				this.voucherService.update(this.id,this.voucher).then(() => {
					this.$router.push('/admin/voucher');
				});
				return;
			} else {
				this.voucherService.save(this.voucher).then(() => {
					this.$router.push('/admin/voucher');
				});
			}
		},
	},
	created() {
		this.init();
	}
});
</script>