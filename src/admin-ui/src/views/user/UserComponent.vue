<template>
	<div class="row">
		<p class="h2">Quản lý người dùng</p>
	</div>
	<hr>
	<div class="row">
		<div class="input-group mb-3 col">
			<input type="text" class="form-control" placeholder="Mời bạn nhập email hoặc username"
			       v-model="request.email">
			<button class="btn btn-outline-secondary" type="button" id="button-addon2" @click.prevent="find()">
				<i class="bi bi-search"></i>
				Tìm kiếm
			</button>
		</div>
		<div class="col"></div>
		<div class="col">
			<button class="btn btn-success m-1" @click.prevent="$router.push('/admin/user/new')">
				<i class="bi bi-pencil-fill"></i>
				Thêm mới
			</button>
		</div>
	</div>
	<div class="table table-responsive" id="table">
		<table class="table table-bordered table-hover table-striped">
			<thead>
			<tr>
				<th>
					tài khoản
				</th>
				<th>
					Họ tên
				</th>
				<th>
					Email
				</th>
				<th>
					Số điện thoại
				</th>
				<th>
					Địa chỉ
				</th>
				<th>
					Ngày tạo
				</th>
				<th>
					Ngày cập nhật
				</th>
				<th>
					Trạng thái
				</th>
				<th>
					Thao tác
				</th>
			</tr>
			</thead>
			<tbody>
			<tr class="align-middle" v-for="item in userList.content">
				<td>
					{{ item.username }}
				</td>
				<td>
					{{ item.fullName }}
				</td>
				<td>
					{{ item.email }}
				</td>
				<td>
					{{ item.phoneNumber }}
				</td>
				<td>
					{{ item.address }}
				</td>
				<td>
					{{ dateTime(item.created + '') }}
				</td>
				<td>
					{{ dateTime(item.modified + '') }}
				</td>
				<td>
					<span v-if="item.status === UserStatusConstant.STATUS_ACTIVE" class="badge bg-success">Hoạt động</span>
					<span v-else class="badge bg-danger">Khóa</span>
				</td>
				<td>
					<button class="btn btn-success btn-sm m-1" @click.prevent="$router.push('/admin/user/'+item.id)">
						<i class="bi bi-pencil-fill"></i>
					</button>
					<button class="btn btn-danger btn-sm m-1" @click.prevent="deleteById(item.id)">
						<i class="bi bi-trash-fill"></i>
					</button>
				</td>
			</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
		<nav class="col">
			<ul class="pagination">
				<li class="page-item">
					<a class="page-link" @click.prevent="getUser(page--,size)">Previous</a>
				</li>
				<li class="page-item" v-for="item in userList?.totalPages">
					<a class="page-link" href="#" @click="getUser(item-1,size)">{{ item }}</a>
				</li>
				<li class="page-item">
					<a class="page-link" href="#" @click.prevent="getUser(page++,size)">Next</a>
				</li>
			</ul>
			<div class="p-2 col pagination">
				Số phần tử
				<select v-model="size" @change.prevent="onChange()">
					<option :value="10">10</option>
					<option :value="25">25</option>
					<option :value="50">50</option>
				</select>
			</div>
		</nav>
	</div>
</template>

<script lang="ts">
import {defineComponent} from 'vue';
import {toast} from 'vue3-toastify';
import {Pageable} from "@/core/model/core.base";
import {UserService} from '@/core/service/user.service';
import {User, UserFindRequest, UserStatusConstant} from "@/core/model/user.model";
import moment from 'moment/moment';

export default defineComponent({
	name: "UserComponent",
	data() {
		return {
			UserStatusConstant: UserStatusConstant,
			userList: new Pageable<User>(),
			user: new User(),
			loading: false,
			search: '' as string,
			userService: new UserService(),
			page: 0 as number,
			size: 10 as number,
			isUpdate: false as boolean,
			request: new UserFindRequest()
		}
	},
	methods: {
		getUser(page: number, size: number) {
			this.userService.getUser(page, size).then(data => {
				this.userList = data;
			}).catch(error => {
				toast.error(error.message);
			})
		},
		dateTime(value: string) {
			if (value == null || value == undefined || value === '') {
				return '';
			} else {
				return moment(value).format('DD-MM-YYYY HH:mm:ss');
			}

		},
		onChange() {
			this.getUser(this.page, this.size);
		},
		deleteById(id: number) {
			if (confirm("Bạn có chắc chắn muốn xóa không?")) {
				this.userService.deleteUser(id).then(data => {
					if (data === true) {
						toast.success("Xóa thành công");
						this.getUser(this.page, this.size);
					}
				}).catch(error => {
					toast.error(error.message);
				})
			}
		}, userAdded(user: User) {
			this.userList.content?.push(user);
		},
		find() {
			if (this.request.email === '' || this.request.email === undefined) {
				this.getUser(this.page, this.size);
				return;
			}
			this.request.username = this.request.email;
			this.userService.findByUsernameOrEmail(this.request).then(data => {
				this.userList.content = data;
			}).catch(error => {
				toast.error(error.message);
			})
		}
	},
	created() {
		this.getUser(this.page, this.size);
	}
})
</script>

<style scoped>
#table {
	padding: 0px;
}
</style>