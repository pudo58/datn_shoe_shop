<template>
	<div class="h2">Thêm mới sản phẩm</div>
	<hr>
	<form @submit.prevent="onSubmit" class="form-control">
		<div class="form-group">
			<div class="row">
				<div class="col">
					<div class="form-floating mb-3">
						<input
							type="text"
							class="form-control"
							id="floatingProductName"
							v-model="productDto.name"
							placeholder="Vui lòng điền tên sản phẩm"
						/>
						<label class="col-form-label" for="floatingProductName"
						>Tên sản phẩm
							<span class="text-bg-danger">*</span>
						</label>
					</div>
				</div>
				<div class="col">
					<div class="form-floating mb-3">
						<input
							type="text"
							class="form-control"
							id="floatingProductPrice"
							v-model="productDto.price"
							placeholder="Vui lòng điền giá sản phẩm"
						/>
						<label class="col-form-label" for="floatingProductPrice"
						>Giá sản phẩm
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
						<select
							class="form-control"
							id="floatingProductPublisher"
							v-model="productDto.publisher.id"
							:disabled="isUpdate"
						>
							<option :value="0" disabled>Vui lòng chọn hãng sản xuất</option>
							<option v-for="item in publisherList" :value="item.id">
								{{ item.name }}
							</option>
						</select>
						<label class="col-form-label" for="floatingProductPublisher"
						>Hãng sản xuất
							<span class="text-bg-danger">*</span>
						</label>
					</div>
				</div>
				<div class="col">
					<div class="form-floating mb-3">
						<select
							class="form-control"
							id="floatingProductCategory"
							v-model="productDto.category.id"
							:disabled="isUpdate"
						>
							<option :value="0" disabled>Vui lòng chọn danh mục</option>
							<option v-for="item in categoryList" :value="item.id">
								{{ item?.name }}
							</option>
						</select>
						<label class="col-form-label" for="floatingProductCategory"
						>Danh mục
							<span class="text-bg-danger">*</span>
						</label>
					</div>
				</div>
			</div>
		</div>
		<div class="form-group" v-for="(size, index) in sizeList" :key="index">
			<div class="row">
				<div class="col">
					<div class="form-floating mb-3">
						<input type="text" class="form-control" :id="'floatingSizeName'+index"
						       v-model="productDto.sizeList[index].size"
						       :placeholder="'Vui lòng điền tên kích thước '+index"/>
						<label class="col-form-label" :for="'floatingSizeName'+index">Tên kích thước {{ index + 1 }}
							<span class="text-bg-danger">*</span>
						</label>
					</div>
				</div>
				<div class="col">
					<div class="form-floating mb-3">
						<input type="number" class="form-control" :id="'floatingSizeQuantity'+index"
						       v-model="productDto.sizeList[index].quantity"
						       :placeholder="'Vui lòng điền số lượng cho kích thước '+index"/>
						<label class="col-form-label" :for="'floatingSizeQuantity'+index">Số lượng
							<span class="text-bg-danger">*</span>
						</label>
					</div>
				</div>
			</div>
		</div>
		<div class="form-group mb-3">
			<div class="row">
				<div class="col">
					<div class="form-floating mb-3">
						<input type="file" class="form-control" id="productImage" @change="handleImageChange"/>
						<label class="col-form-label" for="productImage">Ảnh sản phẩm</label>
					</div>
				</div>
				<div class="col">
					<div class="form-floating mb-3">
						<input type="number" class="form-control" id="floatingSizeQuantity"
						       v-model="productDto.discount" placeholder="% giảm giá"/>
						<label class="col-form-label" for="floatingSizeQuantity">% giảm giá
						</label>
					</div>
				</div>
			</div>
		</div>
		<div class="form-group">
			<div class="form-group">
				<div class="form-floating mb-3">
					<input type="text" class="form-control" id="floatingProductDesc"
					       v-model="productDto.description" placeholder="Vui lòng điền mô tả">
					<label class="col-form-label" for="floatingProductDesc">Mô tả
					</label>
				</div>
			</div>
			<button type="submit" class="btn btn-success d-flex justify-content-start">Lưu</button>
		</div>
	</form>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {ProductService} from "@/core/service/product.service";
import {ProductDto} from "@/core/model/product.model";
import {CategoryService} from "@/core/service/category.service";
import {Category} from "@/core/model/category.model";
import {PublisherService} from "@/core/service/publisher.service";
import {Publisher} from "@/core/model/publisher.model";
import {ProductDetailService} from "@/core/service/product-detail.service";
import {Size, SizeDto} from "@/core/model/size.model";
import {SizeService} from "@/core/service/size.service";
import {Attribute} from "@/core/model/attribute.model";
import {AttributeService} from "@/core/service/attribute.service";

export default defineComponent({
	name: "ProductDetailActionComponent",
	components: {},
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
			sizeList: new Array<Size>(),
			sizeListSelected: [] as string[],
			quantityList: [] as number[],
			attributeList: new Array<Attribute>(),
			attributeService: new AttributeService(),
			id : 0 as number,
			isUpdate: false as boolean
		}
	},
	methods: {
		formatMoney(value: number) {
			return value.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
		},
		onSubmit(e: any) {
			let file = e.target[4].files[0];
			this.sizeListSelected.length = this.quantityList.length;
			this.sizeListSelected.forEach((item, index) => {
				let sizeDto = new SizeDto();
				sizeDto.size = item;
				sizeDto.quantity = this.quantityList[index];
				this.productDto.sizeList?.push(sizeDto);
			});
			this.productService.save(this.productDto).then((res) => {
				this.$emit('onSubmit', true);
				this.productService.addImage(res.product.id, file).then((res) => {
					this.$emit('onSubmit', true);
				});
			});
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
		changeCategoryCombo() {
			this.attributeService.findAllByCategoryId(this.productDto.categoryId || 0).then((res) => {
				this.attributeList = res;
			});
		}
	},
	created() {
		this.id = Number(this.$route.params.id);
		if(this.id !== 0 && !isNaN(this.id)){
			this.isUpdate = true;
			this.productService.findById(this.id).then((res) => {
				this.productDto = res;
			});
		}
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