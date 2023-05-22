<template>
	<br><br><br><br>
	<div class="container mb-4">
		<div class="row">
			<div class="col-md-12">
				<h1 class="text-center">Voucher của tôi</h1>
			</div>
		</div>
		<div class="row">
			<table class="table table-bordered table-hover table-striped">
				<thead>
				<tr>
					<th>
						STT
					</th>
					<th>
						Mã voucher
					</th>
					<th>
						Tên voucher
					</th>
					<th>
						Mô tả
					</th>
					<th>
						Ngày bắt đầu
					</th>
					<th>
						Ngày kết thúc
					</th>
				</tr>
				</thead>
				<tbody>
				<tr v-if="voucherList && voucherList?.length > 0" class="align-middle"
				    v-for="(item, index) in voucherList">
					<td>
						{{ index + 1 }}
					</td>
					<td>
						{{ item.code }}
					</td>
					<td>
						{{ item.name }}
					</td>
					<td>
						{{ item.description }}
					</td>
					<td>
						{{ formatDate(item.effectFrom) }}
					</td>
					<td>
						{{ formatDate(item.effectUntil) }}
					</td>
				</tr>
				<tr v-else>
					<td colspan="7" class="text-center">
						<p class="text-danger">Không có voucher nào</p>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
	</div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {Voucher} from "@/core/model/voucher.model";
import {VoucherService} from "@/core/service/voucher.service";
export default defineComponent({
	name: "VoucherComponent",
	data() {
		return {
			voucherList: [] as Voucher[],
			loading: false,
			voucherService: new VoucherService(),
			userId : Number(localStorage.getItem('userId') as string)
		}
	},
	methods: {
		getVoucher() {
			this.loading = true;
			this.voucherService.finAllByUserId(this.userId).then(res => {
				this.voucherList = res;
			}).catch(err => {
				console.log(err);
			});
			this.loading = false;
		},
		formatDate(date: Date) {
			return new Date(date).toLocaleDateString();
		}
	},
	created() {
		this.getVoucher();
	}
})
</script>

<style scoped>

</style>