<template>
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
		<button type="submit" class="btn btn-success">Lưu</button>
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
			category : new Category()
		}
	},
	methods: {
		addCategory() {
			if (this.category.id != null) {
				this.categoryService.update(this.category).then((res) => {
					toast.success("Cập nhật thành công");
				});
				return;
			}
			this.categoryService.save(this.category).then((res) => {
				this.$emit('added-category', res);
			});

		},
		openModal() {
			document.getElementById('show-modal')?.click();
		}
	},
	created() {
		alert("created");
	}
})
</script>