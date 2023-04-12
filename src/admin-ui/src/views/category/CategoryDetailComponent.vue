<template>
	<div class="h2">Chi tiết danh mục</div>
	<hr>
	<form @submit.prevent="addCategory()">
		<div class="form-floating mb-3">
			<input type="text" class="form-control" id="floatingCategoryName" v-model="category.name">
			<label for="floatingCategoryName">Tên danh mục</label>
		</div>
		<div class="form-floating mb-3">
              <textarea class="form-control" placeholder="Mô tả ...." id="floatingDesc"
                        v-model="category.description"></textarea>
			<label for="floatingDesc">Mô tả</label>
		</div>
		<button type="submit" class="btn btn-success d-flex float-start">Lưu</button>
	</form>
</template>

<script lang="ts">
import {defineComponent, PropType} from 'vue'
import {CategoryService} from "@/core/service/category.service";
import {Category} from "@/core/model/category.model";
import {toast} from 'vue3-toastify';

export default defineComponent({
	name: 'CategoryDetailComponent',
	data() {
		return {
			categoryService: new CategoryService(),
			openModalDialog: false,
			category : new Category(),
			id : 0 as number
		}
	},
	methods: {
		addCategory() {
			if (this.category.id !== 0) {
				this.categoryService.update(this.category).then((res) => {
					toast.success("Cập nhật thành công");
					return;
				});
			}else{
				// this.categoryService.save(this.category).then((res) => {
				// 	this.$emit('added-category', res);
				// });
			}
		}
	},
	created() {
		this.id = Number.parseInt(this.$route.params.id as string);
		if (this.id !== 0) {
			this.categoryService.findById(this.id).then((res) => {
				this.category = res;
			});
		}
	}
})
</script>