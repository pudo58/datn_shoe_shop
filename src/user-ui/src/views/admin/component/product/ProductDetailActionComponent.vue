<template>
	<!-- Button trigger modal -->
	<button v-show="false" type="button" class="btn btn-primary" data-bs-toggle="modal"
	        data-bs-target="#create-product"
	        id="show-modal">
		Launch static backdrop modal
	</button>

	<!-- Modal -->
	<div class="modal hide" id="create-product" aria-hidden="true">
		<div class="modal-dialog modal-dialog-xl justify-content-center">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="staticBackdropLabel">Chi tiết sản phẩm</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form @submit.prevent="onSubmit">
						<div class="form-group">
							<div class="row">
								<div class="col">
									<div class="form-floating mb-3">
										<input type="text" class="form-control" id="floatingProductName"
										       v-model="productDto.name" placeholder="Vui lòng điền tên sản phẩm">
										<label class="col-form-label" for="floatingProductName">Tên sản phẩm
											<span class="text-bg-danger">*</span>
										</label>
									</div>
								</div>
								<div class="col">
									<div class="form-floating mb-3">
										<input type="text" class="form-control" id="floatingProductPrice"
										       v-model="productDto.price" placeholder="Vui lòng điền giá sản phẩm">
										<label class="col-form-label" for="floatingProductPrice">Giá sản phẩm
											<span class="text-bg-danger">*</span>
										</label>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col">
									<div class="form-floating mb-3">
										<select class="form-control" id="floatingProductPublisher"
										        v-model="productDto.publisherId">
											<option :value="0" disabled>Vui lòng chọn hãng sản xuất</option>
											<option v-for="item in publisherList" :value="item.id">{{ item.name }}
											</option>
										</select>
										<label class="col-form-label" for="floatingProductPublisher">Hãng sản xuất
											<span class="text-bg-danger">*</span>
										</label>
									</div>
								</div>
								<div class="col">
									<div class="form-floating mb-3">
										<select class="form-control" id="floatingProductCategory"
										        v-model="productDto.categoryId">
											<option :value="0" disabled>Vui lòng chọn danh mục</option>
											<option v-for="item in categoryList" :value="item?.id">
												{{ item?.name }}
											</option>
										</select>
										<label class="col-form-label" for="floatingProductCategory">Danh mục
											<span class="text-bg-danger">*</span>
										</label>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col">
									<label class="col-form-label" for="floatingProductCategory">Size
										<span class="text-bg-danger">*</span>
									</label>
									<div>
										<input type="checkbox" value="39">
										<span>
											39
										</span>
										<i class="bi bi-plus" @click="addSize" role="button"></i>
									</div>

								</div>
								<div class="col">
									<label class="col-form-label" for="floatingProductCategory">Số lượng
										<span class="text-bg-danger">*</span>
									</label>
									<input type="number" v-model="productDto.sizeList">
								</div>
							</div>

						</div>
						<div class="form-group">
							<div class="form-floating mb-3">
								<input type="text" class="form-control" id="floatingProductDesc"
								       v-model="productDto.description" placeholder="Vui lòng điền mô tả">
								<label class="col-form-label" for="floatingProductDesc">Mô tả
								</label>
							</div>
						</div>
						<button type="submit" class="btn btn-success">Lưu</button>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {toast} from "vue3-toastify";
import {ProductService} from "@/core/service/product.service";
import {ProductDto} from "@/core/model/product.model";
import {CategoryService} from "@/core/service/category.service";
import {Category} from "@/core/model/category.model";
import {PublisherService} from "@/core/service/publisher.service";
import {Publisher} from "@/core/model/publisher.model";
import {ProductDetailService} from "@/core/service/product-detail.service";
import {Size} from "@/core/model/size.model";
import {SizeService} from "@/core/service/size.service";

export default defineComponent({
	name: "ProductDetailActionComponent",
	data() {
		return {
			productDto: new ProductDto(),
			productDetailService: new ProductDetailService(),
			productService: new ProductService(),
			categoryService: new CategoryService(),
			sizeService: new SizeService(),
			publisherService: new PublisherService(),
			categoryList: [] as Category[],
			publisherList: [] as Publisher[],
			sizeList : new Array<Size>(),
			sizeListSelected: [] as Size[],
			quantityList: [] as number[],
			categoryId: 0,
			publisherId: 0
		}
	},
	methods: {
		formatMoney(value: number) {
			return value.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
		},
		onSubmit() {
			this.productService.save(this.productDto).then((res) => {
				toast.success("Thêm mới thành công");
			})
		},
		getCategoryList() {
			this.categoryService.findAllCategory().then((res) => {
				this.categoryList = res;
			})
		},
		getPublisherList() {
			this.publisherService.findAllPublisher().then((res) => {
				this.publisherList = res;
			})
		},
		getSizeList() {
			this.sizeService.findAll().then((res) => {
				this.sizeList = res;
			})
		},
		addSize(){
			this.sizeList?.push(new Size());
		},
		openModal() {
			document.getElementById('show-modal')?.click();
		}
	},
	created() {
		this.getCategoryList();
		this.getPublisherList();
		this.getSizeList();
	}
})

</script>

<style scoped>
.modal-dialog-xl {
	max-width: 70%;
	width: 70%;
}
</style>