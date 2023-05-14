<template>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
	<div class="form-gap"></div>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="text-center">
							<h3><i class="fa fa-lock fa-4x"></i></h3>
							<h2 class="text-center">Quên mật khẩu ?</h2>
							<p>Bạn có thể lấy mật khẩu tại đây.</p>
							<div class="panel-body">
								<form @submit.prevent="onSubmit()" id="register-form" role="form" autocomplete="off" class="form" method="post">
									<div class="form-group">
										<div class="input-group">
											<span class="input-group-addon"><i
												class="glyphicon glyphicon-envelope color-blue"></i></span>
											<input id="email" name="email" placeholder="email address"
											       class="form-control" type="email" v-model="changePasswordRequest.email">
										</div>
									</div>
									<div class="form-group">
										<input name="recover-submit" class="btn btn-lg btn-primary btn-block"
										       :value="count ? secondCount : 'Lấy mã'" type="submit" :disabled="count">
									</div>
									<input type="hidden" class="hide" name="token" id="token" value="">
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<style scoped>
.form-gap {
	padding-top: 70px;
}
</style>

<script lang="ts">
import { defineComponent } from 'vue'
import {toast} from 'vue3-toastify'
import axios from "axios";
import {ChangePasswordRequest} from "@/core/model/user.model";
export default defineComponent({
	name: 'ForgotPassword',
	data() {
		return {
			secondCount: 120,
			changePasswordRequest : new ChangePasswordRequest(),
			count : false
		}
	},
	methods: {
		onSubmit(){
			const response = axios.post('/api/user/sendCode', this.changePasswordRequest);
			response.then((res)=>{
				if (res.data.status == 200) {
					toast.success(res.data?.message);
					this.count = true;
					setInterval(() => {
						this.secondCount--;
						if (this.secondCount === 0) {
							this.count = false;
							this.secondCount = 120;
						}
					}, 1000);
				}else{
					toast.error(res.data?.message);
				}
			})

		}
	}
})
</script>