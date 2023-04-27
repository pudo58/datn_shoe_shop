<template>
	<div class="h2" v-if="!isUpdate">Tạo mới nhà sản xuất</div>
	<div class="h2" v-else>Cập nhật nhà sản xuất</div>
	<hr>
	<form @submit.prevent="addPublisher()" class="form-control">
		<div class="form-floating mb-3">
			<input type="text" class="form-control" id="floatingPublisherName" v-model="brand.name">
			<label for="floatingPublisherName">Tên nhà sản xuất</label>
		</div>
		<div class="form-floating mb-3">
			<input class="form-control" placeholder="example.com" id="floatingDesc"
			       v-model="brand.website">
			<label for="floatingDesc">Link website</label>
		</div>
		<button type="submit" class="btn btn-success d-flex justify-content-start">Lưu</button>
	</form>
</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {BrandService} from "@/core/service/brand.service";
import {Brand} from "@/core/model/brand.model";
import {toast} from 'vue3-toastify';

export default defineComponent({
	name: 'brandDetailComponent',
	data() {
		return {
			brandService: new BrandService(),
			isUpdate : false as boolean,
			id : 0 as number,
			brand : new Brand()
		}
	},
	methods: {
		addPublisher() {
			if (this.isUpdate) {
				this.brandService.update(this.brand).then((res) => {
					toast.success("Cập nhật thành công");
					this.$router.push('/admin/brand');
				});
				return;
			} else {
				this.brandService.save(this.brand).then((res) => {
					this.$router.push('/admin/brand');
				});
			}
		},
	},
	created() {
		this.id = Number.parseInt(this.$route.params.id as string);
		if (this.id !== 0 && !isNaN(this.id)) {
			this.isUpdate = true;
			this.brandService.findById(this.id).then((res) => {
				this.brand = res;
			});
		}
	}
})
</script>