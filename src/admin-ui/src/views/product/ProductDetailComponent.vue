<template>
	<div class="h2">Thêm mới sản phẩm</div>
	<hr>
	<!--	<form @submit.prevent="onSubmit" class="form-control">-->
	<!--		<div class="form-group">-->
	<!--			<label class="col-form-label" for="productName">Tên sản phẩm<span class="text-bg-danger">*</span></label>-->
	<!--			<input type="text" class="form-control" id="productName" v-model="productDto.name" placeholder="Vui lòng điền tên sản phẩm"/>-->
	<!--		</div>-->
	<!--		<div class="form-group">-->
	<!--			<label class="col-form-label" for="productPrice">Giá sản phẩm<span class="text-bg-danger">*</span></label>-->
	<!--			<input type="text" class="form-control" id="productPrice" v-model="productDto.price" placeholder="Vui lòng điền giá sản phẩm"/>-->
	<!--		</div>-->
	<!--		<div class="form-group">-->
	<!--			<label class="col-form-label" for="productPublisher">Hãng sản xuất<span class="text-bg-danger">*</span></label>-->
	<!--			<select class="form-control" id="productPublisher" v-model="productDto.publisher.id" :disabled="isUpdate">-->
	<!--				<option :value="0" disabled>Vui lòng chọn hãng sản xuất</option>-->
	<!--				<option v-for="item in publisherList" :value="item.id">{{ item.name }}</option>-->
	<!--			</select>-->
	<!--		</div>-->
	<!--		<div class="form-group">-->
	<!--			<label class="col-form-label" for="productCategory">Danh mục<span class="text-bg-danger">*</span></label>-->
	<!--			<select class="form-control" id="productCategory" v-model="productDto.category.id" :disabled="isUpdate">-->
	<!--				<option :value="0" disabled>Vui lòng chọn danh mục</option>-->
	<!--				<option v-for="item in categoryList" :value="item.id">{{ item?.name }}</option>-->
	<!--			</select>-->
	<!--		</div>-->
	<!--		<div class="form-group" v-for="(size, index) in sizeList" :key="index">-->
	<!--			<label class="col-form-label" :for="'sizeName'+index">Tên kích thước {{ index + 1 }}<span class="text-bg-danger">*</span></label>-->
	<!--			<input type="text" class="form-control" :id="'sizeName'+index" v-model="productDto.sizeList[index].size" :placeholder="'Vui lòng điền tên kích thước '+index"/>-->
	<!--			<label class="col-form-label" :for="'sizeQuantity'+index">Số lượng<span class="text-bg-danger">*</span></label>-->
	<!--			<input type="number" class="form-control" :id="'sizeQuantity'+index" v-model="productDto.sizeList[index].quantity" :placeholder="'Vui lòng điền số lượng cho kích thước '+index"/>-->
	<!--		</div>-->
	<!--		<div class="form-group">-->
	<!--			<label class="col-form-label" for="productImage">Ảnh sản phẩm</label>-->
	<!--			<input type="file" class="form-control" id="productImage" @change="handleImageChange"/>-->
	<!--		</div>-->
	<!--		<div class="form-group">-->
	<!--			<label class="col-form-label" for="discount">Giảm giá (%)</label>-->
	<!--			<input type="number" class="form-control" id="discount" v-model="productDto.discount" placeholder="% giảm giá"/>-->
	<!--		</div>-->
	<!--		<div class="form-group">-->
	<!--			<label class="col-form-label" for="desc">Mô tả</label>-->
	<!--			<input type="text" class="form-control" id="desc" v-model="productDto.description" placeholder="Vui lòng điền mô tả"/>-->
	<!--		</div>-->
	<!--		<button type="submit" class="btn btn-success d-flex justify-content-start">Lưu</button>-->
	<!--	</form>-->
	<section class="panel panel-default">
		<div class="panel-body">
			<form class="form-horizontal" role="form" @submit.prevent="onSubmit($event)">
				<div class="form-group row mb-3 align-items-start d-flex justify-content-center">
					<label for="name" class="col-sm-2 control-label fw-bold align-self-center text-start">Tên sản phẩm
						(*)</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" id="name" placeholder="Tên sản phẩm"
						       v-model="productDto.name">
					</div>
				</div>
				<div class="form-group row mb-3 align-items-start d-flex justify-content-center">
					<label for="price" class="col-sm-2 control-label fw-bold align-self-center text-start">Giá sản phẩm
						(*)</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" name="name" id="price" placeholder="giá sản phẩm"
						       v-model="productDto.price">
					</div>
				</div>
				<div class="form-group row mb-3 align-items-start d-flex justify-content-center">
					<label for="discount" class="col-sm-2 control-label fw-bold align-self-center text-start">% giảm giá
						(*)</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" name="name" id="discount" placeholder="% giảm giá"
						       v-model="productDto.discount">
					</div>
				</div>
				<div class="form-group row mb-3 align-items-start d-flex justify-content-center">
					<label for="category" class="col-sm-2 control-label fw-bold align-self-center text-start">Danh mục
						(*)</label>
					<div class="col-sm-5">
						<select id="category" class="form-select-sm form-control" v-model="productDto.categoryId"
						        @change.prevent="changeCategoryCombo()">
							<option v-for="(item,index) in categoryList" :value="item.id">{{ item.name }}</option>
						</select>
					</div>
				</div>
				<template v-if="attributeList && attributeList?.length > 0">
					<div class="form-group row mb-3 align-items-start d-flex justify-content-center"
					     v-for="(item,index) in attributeList">
						<label :for="item.name"
						       class="col-sm-2 control-label fw-bold align-self-center text-start">{{
								item.name
							}}</label>
						<div class="col-sm-5">
							<input v-if="item.type === ATTRIBUTE_TYPE.STRING || item.type === ATTRIBUTE_TYPE.DOUBLE" type="text" :id="item.name" class="form-control" v-model="attributeValues[item.name]"/>
							<input v-if="item.type === ATTRIBUTE_TYPE.DATE" type="date" :id="item.name" class="form-control" v-model="attributeValues[item.name]"/>
							<input v-if="item.type === ATTRIBUTE_TYPE.DATETIME" type="datetime-local" :id="item.name" class="form-control" v-model="attributeValues[item.name]"/>
							<input v-if="item.type === ATTRIBUTE_TYPE.INTEGER" type="number" :id="item.name" class="form-control" v-model="attributeValues[item.name]"/>
							<template v-if="item.type === ATTRIBUTE_TYPE.BOOLEAN">
								<select :id="item.name" class="form-select-sm form-control" v-model="attributeValues[item.name]">
									<option value="true">Có</option>
									<option value="false">Không</option>
								</select>
							</template>
						</div>
					</div>
				</template>
				<div class="form-group row mb-3 align-items-start d-flex justify-content-center">
					<label for="publisher" class="col-sm-2 control-label fw-bold align-self-center text-start">Hãng
						(*)</label>
					<div class="col-sm-5">
						<select id="publisher" class="form-select-sm form-control" v-model="productDto.publisherId">
							<option v-for="(item,index) in publisherList" :value="item.id">{{ item.name }}</option>
						</select>
					</div>
				</div>
				<div class="form-group row mb-3 align-items-start d-flex justify-content-center">
					<label for="imageThumble" class="col-sm-2 control-label fw-bold align-self-center text-start">Chọn
						ảnh (*)</label>
					<div class="col-sm-5">
						<input type="file" id="imageThumble" class="form-control-sm form-control"/>
					</div>
				</div>
				<div class="form-group row mb-3 d-flex justify-content-center">
					<label for="desc" class="col-sm-2 control-label fw-bold text-start">Mô tả</label>
					<div class="col-sm-5">
						<textarea id="desc" class="form-control-sm form-control"
						          v-model="productDto.description"></textarea>
					</div>
				</div>
				<hr>
				<div class="form-group row mb-3">
					<div class="col-sm-offset-3 col-sm-9">
						<button type="submit" class="btn btn-primary">Lưu</button>
					</div>
				</div>
			</form>
		</div>
	</section>
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
import {Attribute, ATTRIBUTE_TYPE} from "@/core/model/attribute.model";
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
			id: 0 as number,
			isUpdate: false as boolean,
			attributeValue: new Map<string, string>(),
			ATTRIBUTE_TYPE : ATTRIBUTE_TYPE
		}
	},
	methods: {
		formatMoney(value: number) {
			return value.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
		},
		onSubmit($event: any) {
			this.sizeListSelected.length = this.quantityList.length;
			this.sizeListSelected.forEach((item, index) => {
				let sizeDto = new SizeDto();
				sizeDto.size = item;
				sizeDto.quantity = this.quantityList[index];
				this.productDto.sizeList?.push(sizeDto);
			});
			this.productDto.attributeValues = this.attributeValues;
			console.log(this.productDto)
			this.productService.save(this.productDto).then((res) => {
				let file = $event.target[5].files[0];
				this.productService.addImage(res.product.id, file).then((res) => {
				});
			});
		},
		getCategoryList() {
			this.categoryService.findAllCategory().then((res) => {
				this.categoryList = res;
			});
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
		if (this.id !== 0 && !isNaN(this.id)) {
			this.isUpdate = true;
			this.productService.findById(this.id).then((res) => {
				this.productDto = res;
			});
		}
		this.getCategoryList();
		this.getPublisherList();
		this.getSizeList();
	},
	computed: {
		attributeValues() {
			let values: { [key: string]: string } = {}; // sử dụng chỉ mục
			this.attributeList.forEach((item) => {
				if (item.name) {
					values[item.name] = this.attributeValue.get(item.name) || '';
				}
			});
			return values;
		},
	},
})

</script>

<style scoped>
.modal-dialog-xl {
	max-width: 70%;
	width: 70%;
}
</style>
