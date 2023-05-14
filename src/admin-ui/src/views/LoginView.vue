<template>
	<div class="container">
		<div class="row">
			<div class="col">

			</div>
			<div class="col">
				<div class="card">
					<div class="card-header text-center">
						<h3 class="display-5">Login Form</h3>
					</div>
					<div class="card-body">
						<label for="user">Username</label>
						<input type="text" class="form-control form-control-sm" id="user" v-model="user"/>
						<label for="password">Password</label>
						<input type="text" class="form-control form-control-sm" id="password" v-model="password"/>
					</div>
					<div class="card-footer">
						<button class="btn btn-primary btn-block" id="login" @click.prevent="login()">Login</button>
					</div>
				</div>
			</div>
			<div class="col">
			</div>
		</div>
	</div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {UserService} from "@/core/service/user.service";
export default defineComponent({
	name: 'Login',
	data() {
		return {
			user: '',
			password: '',
			userService: new UserService()
		}
	},
	methods: {
		async login() {
			const res = await this.userService.login(this.user,this.password);
			if (res !== null  && res !== undefined) {
				this.$router.push('/');
			}
		}
	},
});
</script>

<style scoped>
div.container div.row div.col div.card {
	margin-top: 15%;
}
div.card-body{
	padding: 15px 20px;
}
div.card-footer{
	padding: 10px 20px;
}
</style>