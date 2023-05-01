<template>
	<div class="pageUpdatePassword">
		<span class="companyName">Đặt lại mật khẩu</span>
		<span class="title">Điền mật khẩu mới để đặt lại mật khẩu</span>
		<div class="formPassword">
			<input v-if="code.length === 0" type="text" class="newPass" placeholder="Mã xác nhận" v-model="changePasswordRequest.code">
			<input type="password" class="newPass" placeholder="Mậy khẩu mới" v-model="changePasswordRequest.newPassword">
			<input type="password" class="reNewPass" placeholder="Nhập lại mật khẩu" v-model="changePasswordRequest.confirmPassword">
		</div>
		<div>
			<button class="updatePass" @click.prevent="onSubmit()">Cập nhật mật khẩu</button>
		</div>
	</div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import axios from "axios";
import {toast} from "vue3-toastify";
import {ChangePasswordRequest} from "@/core/model/user.model";

export default defineComponent({
	name: 'RepeatPassword',
	data() {
		return {
			changePasswordRequest : new ChangePasswordRequest(),
			code : this.$route.params.code as string,
			currentEmail : this.$route.params.email as string
		}
	},
	methods:{
			onSubmit(){
				const response = axios.post('/api/user/resetPassword', this.changePasswordRequest);
				response.then((res)=>{
					if (res.data.status == 200) {
						toast.success(res.data?.message);
					} else {
						toast.error(res.data?.message);
					}
				}).catch((err)=>{
					console.log(err)
				})
			}

	},
	created() {
		this.changePasswordRequest.email = this.currentEmail;
		this.changePasswordRequest.code = this.code;
	}
});
</script>

<style scoped>

@media only screen and (min-width: 768px) {
	.pageUpdatePassword{
		margin: 5% 10%;
	}
	.formPassword{
		margin-left: 10%;
		margin-right: 10%;
	}
}
@media only screen and (min-width: 1024px){

	.formPassword{
		margin-left: 23%;
		margin-right: 23%;
	}
}
.pageUpdatePassword{
	font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
	padding: 32px 6%;
	color : #444444;
	font-size: 18px;
	text-align: center;
}
span{
	display: block;
}
.companyName{
	font-size: 38px;
	font-weight: bold;
	text-align: center;
	margin-bottom: 32px;
}
.title{
	font-size: 20px;
}
.userEmail{
	margin-bottom: 20px;
	margin-top: 10px;
}
.formPassword{
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;

}
input{
	border-radius: 5px;
	display: block;
	width: 80%;
	margin-bottom:20px;
	border: 1px solid #979797;
	padding: 16px 32px;
}
::-webkit-input-placeholder {
	text-align: center;
}

:-moz-placeholder { /* Firefox 18- */
	text-align: center;
}

::-moz-placeholder {  /* Firefox 19+ */
	text-align: center;
}

:-ms-input-placeholder {
	text-align: center;
}
input:focus{
	border: 1px solid #004a86;

}

button.updatePass{
	margin-top: 10px;
	font-size: 18px;
	background-color: #004a86;
	color: white;
	padding: 16px 32px;
	border: 1px solid;
	border-radius: 6px;
}
</style>
