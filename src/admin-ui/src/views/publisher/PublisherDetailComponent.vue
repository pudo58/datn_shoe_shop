<template>
	<div class="h2" v-if="!isUpdate">Tạo mới nhà sản xuất</div>
	<div class="h2" v-else>Cập nhật nhà sản xuất</div>
	<hr>
	<form @submit.prevent="addPublisher()" class="form-control">
		<div class="form-floating mb-3">
			<input type="text" class="form-control" id="floatingPublisherName" v-model="publisher.name">
			<label for="floatingPublisherName">Tên nhà sản xuất</label>
		</div>
		<div class="form-floating mb-3">
			<input class="form-control" placeholder="example.com" id="floatingDesc"
			       v-model="publisher.website">
			<label for="floatingDesc">Link website</label>
		</div>
		<button type="submit" class="btn btn-success d-flex justify-content-start">Lưu</button>
	</form>
</template>

<script lang="ts">
import {defineComponent} from 'vue'
import {PublisherService} from "@/core/service/publisher.service";
import {Publisher} from "@/core/model/publisher.model";
import {toast} from 'vue3-toastify';

export default defineComponent({
	name: 'publisherDetailComponent',
	data() {
		return {
			publisherService: new PublisherService(),
			isUpdate : false as boolean,
			id : 0 as number,
			publisher : new Publisher()
		}
	},
	methods: {
		addPublisher() {
			if (this.isUpdate) {
				this.publisherService.update(this.publisher).then((res) => {
					toast.success("Cập nhật thành công");
					this.$router.push('/admin/publisher');
				});
				return;
			} else {
				this.publisherService.save(this.publisher).then((res) => {
					this.$router.push('/admin/publisher');
				});
			}
		},
	},
	created() {
		this.id = Number.parseInt(this.$route.params.id as string);
		if (this.id !== 0 && !isNaN(this.id)) {
			this.isUpdate = true;
			this.publisherService.findById(this.id).then((res) => {
				this.publisher = res;
			});
		}
	}
})
</script>