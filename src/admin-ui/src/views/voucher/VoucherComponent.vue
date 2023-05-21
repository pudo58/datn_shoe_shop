<template>
	<div class="row">
		<p class="h2">Quản lý Voucher</p>
	</div>
	<hr>
	<div class="row">
		<div class="input-group mb-3 col">
			<input type="text" class="form-control" placeholder="Mời bạn nhập tên hãng sản xuất" v-model="code">
			<button class="btn btn-outline-secondary" type="button" id="button-addon2" @click.prevent="findByName()">
				<i class="bi bi-search"></i>
				Tìm kiếm
			</button>
		</div>
		<div class="col"></div>
		<div class="col">
			<button class="btn btn-success m-1" @click="this.$router?.push('/admin/voucher/new')">
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
				<th>
					Số lượng
				</th>
				<th>
					Giới hạn cho mỗi người
				</th>
				<th>
					Loại giảm giá
				</th>
				<th>
					Giá trị giảm giá
				</th>
				<th>
					Trạng thái
				</th>
				<th>
					Thao tác
				</th>
			</tr>
			</thead>
			<tbody>
			<tr v-if="voucherList && voucherList?.content?.length > 0" class="align-middle"
			    v-for="item in voucherList.content">
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
				<td>
					{{ item.quantity }}
				</td>
				<td>
					{{ item.limitPerUser }}
				</td>
				<td>
					{{ Voucher.getDiscountTypeText(item.discountType) }}
				</td>
				<td>
					{{ item.discountValue }}
				</td>
				<td>
					{{ Voucher.getStatusText(item.status) }}
				</td>
				<td>
					<i title="Cập nhật" role="button" @click="this.$router?.push('/admin/voucher/' + item.id)"
					   class="bi bi-pencil-fill m-1"></i>
					<i title="Xóa" role="button" @click="deleteById(item.id)" class="bi bi-trash-fill"></i>
				</td>
			</tr>
			<tr v-if="voucherList && voucherList?.content?.length == 0">
				<td colspan="12">
					<p class="h5 text-center">Không có dữ liệu</p>
				</td>
			</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
		<nav class="col">
			<ul class="pagination">
				<li class="page-item">
					<a class="page-link" @click.prevent="prePage">Previous</a>
				</li>
				<li class="page-item" v-for="item in voucherList?.totalPages">
					<a class="page-link" href="#" @click="clickPage(item)">{{ item }}</a>
				</li>
				<li class="page-item">
					<a class="page-link" href="#" @click.prevent="nextPage()">Next</a>
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
import {Pageable} from "@/core/model/core.base";
import {toast} from "vue3-toastify";
import {Voucher} from "@/core/model/voucher.model";
import {VoucherService} from "@/core/service/voucher.service";

export default defineComponent({
	name: 'VoucherComponent',
	components: {
		//BrandDetailComponent,
	},
	data() {
		return {
			voucherList: new Pageable<Voucher>(),
			voucherService: new VoucherService(),
			page: 0,
			size: 10,
			name: '' as string,
			Voucher: Voucher,
			code: '' as string,
		}
	},
	methods: {
		async getVoucher(page: number, size: number) {
			const res = await this.voucherService.findAll(page, size) as Pageable<Voucher>;
			this.voucherList = res;
		},
		deleteById(id: number) {
			if (confirm("Bạn có chắc chắn muốn xóa?")) {
				this.voucherService.delete(id).then((res) => {
					if (res) {
						toast.success("Xóa thành công");
						this.getVoucher(this.page, this.size);
					}
				})
			}
		},
		onChange() {
			this.getVoucher(this.page, this.size);
		},
		async findByName() {
			if (this.code == null || this.code == '')
				await this.getVoucher(this.page, this.size);
			const res = await this.voucherService.findByCode(this.code);
			if (res) {
				this.voucherList.content?.push(res);
			}
		},
		prePage() {
			if (this.page > 0) {
				this.page--;
				this.getVoucher(this.page, this.size);
			}
		},
		nextPage() {
			if (this.voucherList && this.voucherList?.totalPages) {
				if (this.page < this.voucherList.totalPages - 1) {
					this.page++;
					this.getVoucher(this.page, this.size);
				}
			}
		},
		clickPage(page: number) {
			this.page = page;
			this.getVoucher(this.page, this.size);
		},
		formatDate(date: Date) {
			return new Date(date).toLocaleDateString();
		}
	},
	created() {
		this.getVoucher(this.page, this.size);
	}
})
</script>