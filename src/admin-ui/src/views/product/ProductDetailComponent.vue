<template>
	<div class="h2">Thêm mới sản phẩm</div>
	<hr>
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
					<label for="imageThumble" class="col-sm-2 control-label fw-bold align-self-center text-start">Chọn
						ảnh (*)</label>
					<div class="col-sm-5">
						<input type="file" id="imageThumble" class="form-control-sm form-control"/>
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
					<label for="material" class="col-sm-2 control-label fw-bold align-self-center text-start">Chất liệu
						(*)</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" name="name" id="material" placeholder="Chất liệu"
						       v-model="productDto.material">
					</div>
				</div>
				<div class="form-group row mb-3 align-items-start d-flex justify-content-center">
					<label for="material" class="col-sm-2 control-label fw-bold align-self-center text-start">Kiểu dáng
						(*)</label>
					<div class="col-sm-5">
						<input type="text" class="form-control" name="name" id="model" placeholder="Kiểu dáng"
						       v-model="productDto.model">
					</div>
				</div>
				<div class="form-group row mb-3 align-items-start d-flex justify-content-center">
					<label for="category" class="col-sm-2 control-label fw-bold align-self-center text-start">Danh mục
						(*)</label>
					<div class="col-sm-5">
						<select id="category" class="form-select-sm form-control" v-model="productDto.categoryId"
						        @change.prevent="changeCategoryCombo()" :disabled="isUpdate">
							<option v-for="(item,index) in categoryList" :value="item.id">{{ item.name }}</option>
						</select>
					</div>
				</div>
				<template v-if="attributeList && attributeList?.length > 0 && !isUpdate">
					<div class="form-group row mb-3 align-items-start d-flex justify-content-center"
					     v-for="(item,index) in attributeList">
						<label :for="item.name"
						       class="col-sm-2 control-label fw-bold align-self-center text-start">{{
								item.name
							}}</label>
						<div class="col-sm-5">
							<input v-if="item.type === ATTRIBUTE_TYPE.STRING || item.type === ATTRIBUTE_TYPE.DOUBLE"
							       type="text" :id="item.name" class="form-control"
							       v-model="attributeValues[item.name]"/>
							<input v-if="item.type === ATTRIBUTE_TYPE.DATE" type="date" :id="item.name"
							       class="form-control" v-model="attributeValues[item.name]"/>
							<input v-if="item.type === ATTRIBUTE_TYPE.DATETIME" type="datetime-local" :id="item.name"
							       class="form-control" v-model="attributeValues[item.name]"/>
							<input v-if="item.type === ATTRIBUTE_TYPE.INTEGER" type="number" :id="item.name"
							       class="form-control" v-model="attributeValues[item.name]"/>
							<template v-if="item.type === ATTRIBUTE_TYPE.BOOLEAN">
								<select :id="item.name" class="form-select-sm form-control"
								        v-model="attributeValues[item.name]">
									<option value="true">Có</option>
									<option value="false">Không</option>
								</select>
							</template>
						</div>
					</div>
				</template>
				<div class="form-group row mb-3 align-items-start d-flex justify-content-center">
					<label for="brand" class="col-sm-2 control-label fw-bold align-self-center text-start">Hãng
						(*)</label>
					<div class="col-sm-5">
						<select id="brand" class="form-select-sm form-control" v-model="productDto.brandId" :disabled="isUpdate">
							<option v-for="(item,index) in brandList" :value="item.id">{{ item.name }}</option>
						</select>
					</div>
				</div>
				<div class="form-group row mb-3 align-items-start d-flex justify-content-center" v-if="!isUpdate">
					<label class="col-sm-3 control-label fw-bold align-self-center text-center mb-1">Kích cỡ
						(*)</label>
					<div class="col-sm-6 form-check form-check-inline">
						<div v-for="(item,index) in productDto.sizeList" class="d-flex justify-content-start">
							<input type="text" class="form-control w-25  m-1" :id="item.size"
							       v-model="productDto.sizeList[index].size"
							       placeholder="Size" title="size">
							<select v-model="productDto.sizeList[index].color" class="m-1">
								<option value="" disabled>Chọn màu</option>
								<option v-for="(item,index) in colorList" :value="item.name">{{ item.name }}</option>
							</select>
							<input type="text" class="form-control w-25 m-1" :id="item.quantity"
							       v-model="productDto.sizeList[index].quantity"
							       placeholder="Số lượng" title="số lượng">
							<span role="button" class="w-25 d-flex align-items-center justify-content-center">
							  <i class="bi bi-plus-circle-fill" @click.prevent="createSize()" title="Thêm size"></i>
							  <i class="bi bi-x-circle-fill p-2" @click.prevent="removeSize(index)" title="Xóa size"></i>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group row mb-3 d-flex justify-content-center">
					<label for="desc" class="col-sm-2 control-label fw-bold text-start">Mô tả</label>
					<div class="col-sm-5">
						<textarea id="desc" class="form-control-sm form-control"
						          v-model="productDto.description"></textarea>
					</div>
				</div>
				<div class="form-group row mb-3 align-items-start d-flex justify-content-center">
					<label for="gender" class="col-sm-2 control-label fw-bold align-self-center text-start">Giới tính
						(*)</label>
					<div class="col-sm-5">
						<select id="gender" class="form-select-sm form-control" v-model="productDto.gender">
							<option value="1">Nam</option>
							<option value="0">Nữ</option>
						</select>
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
import {BrandService} from "@/core/service/brand.service";
import {Brand} from "@/core/model/brand.model";
import {ProductDetailService} from "@/core/service/product-detail.service";
import {Size, SizeDto} from "@/core/model/size.model";
import {SizeService} from "@/core/service/size.service";
import {Attribute, ATTRIBUTE_TYPE} from "@/core/model/attribute.model";
import {AttributeService} from "@/core/service/attribute.service";
import {toast} from "vue3-toastify";
import {COLOR} from "@/core/model/core.base";

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
			brandService: new BrandService(),
			categoryList: [] as Category[],
			brandList: [] as Brand[],
			sizeList: new Array<Size>(),
			sizeListSelected: [] as string[],
			colorList : COLOR,
			quantityList: [] as number[],
			attributeList: new Array<Attribute>(),
			attributeService: new AttributeService(),
			id: 0 as number,
			isUpdate: false as boolean,
			attributeValue: new Map<string, string>(),
			ATTRIBUTE_TYPE: ATTRIBUTE_TYPE
		}
	},
	methods: {
		formatMoney(value: number) {
			return value.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
		},
		onSubmit($event: any) {
			if(!this.isUpdate){
				this.sizeListSelected.length = this.quantityList.length;
				this.sizeListSelected.forEach((item, index) => {
					let sizeDto = new SizeDto();
					sizeDto.size = item;
					sizeDto.quantity = this.quantityList[index];
					this.productDto.sizeList?.push(sizeDto);
				});
				this.productDto.attributeValues = this.attributeValues;
				this.productService.save(this.productDto).then((res) => {
					let file = $event.target[2].files[0];
					this.productService.addImage(res.product.id, file).then((res) => {
					});
				});
			}else{
				this.productService.update(this.id,this.productDto).then((res) => {
				});
			}
		},
		getCategoryList() {
			this.categoryService.findAllCategory().then((res) => {
				this.categoryList = res;
			});
		},
		createSize() {
			this.productDto.sizeList?.push(new SizeDto());
		},
		getPublisherList() {
			this.brandService.findAllBrand().then((res) => {
				this.brandList = res;
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
		},
		removeSize(index: number) {
			if(this.productDto.sizeList?.length == 1) {
				toast.warning("Sản phẩm phải có ít nhất 1 size");
				return;
			}
			this.productDto.sizeList?.splice(index, 1);
		}
	},
	created() {
		this.id = Number(this.$route.params.id);
		if (this.id !== 0 && !isNaN(this.id)) {
			this.isUpdate = true;
			this.productService.findById(this.id).then((res) => {
				this.productDto = res;
				this.productDto.categoryId = res.category.id;
				this.productDto.brandId = res.brand.id;
			});
		}
		const sizeDto = new SizeDto();
		sizeDto.size = '37';
		sizeDto.quantity = 0;
		this.productDto.sizeList?.push(sizeDto);
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
