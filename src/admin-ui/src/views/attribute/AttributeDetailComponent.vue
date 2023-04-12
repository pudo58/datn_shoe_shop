<template>
	<div v-if="!isUpdate">Thêm mới thuộc tính</div>
	<div v-else>Chỉnh sửa thuộc tính</div>
	<hr>
	<form @submit.prevent="addAttribute()" class="form-control">
		<div class="form-floating mb-3">
			<input type="text" class="form-control" id="floatingCategoryName" v-model="attribute.name">
			<label for="floatingCategoryName">Tên thuộc tính</label>
		</div>
		<div class="form-floating mb-3">
			<select class="form-select" id="floatingDesc" v-model="attribute.type">
				<option value="Integer">Kiểu số nguyên</option>
				<option value="Double">Kiểu số thực</option>
				<option value="Date">Kiểu ngày</option>
				<option value="String">Kiểu Chuỗi</option>
				<option value="Boolean">Kiểu Boolean</option>
			</select>
			<label for="floatingDesc">Kiểu dữ liệu</label>
		</div>
		<button type="submit" class="btn btn-success">Lưu</button>
	</form>
</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {toast} from 'vue3-toastify';
import {Attribute} from "@/core/model/attribute.model";
import {AttributeService} from "@/core/service/attribute.service";

export default defineComponent({
	name: 'AttributeDetailComponent',
	data() {
		return {
			attributeService: new AttributeService(),
			attribute : new Attribute(),
			id : 0 as number,
			isUpdate : false as boolean
		}
	},
	methods: {
		addAttribute() {
			if (this.attribute.id != null) {
				this.attributeService.update(this.attribute).then((res) => {
					toast.success("Cập nhật thành công");
					this.$router.push('/admin/attribute');
				});
				return;
			}
			this.attributeService.save(this.attribute).then((res) => {
				this.$router.push('/admin/attribute');
			});

		}
	},
	created() {
		this.id = Number.parseInt(this.$route.params.id as string);
		console.log(this.id)
		if (this.id !== 0 && !isNaN(this.id)) {
			this.isUpdate = true;
			this.attributeService.findById(this.id).then((res) => {
				this.attribute = res;
			});
		}
	}
})
</script>