import axios from "axios";
import { toast } from "vue3-toastify";
import { ChangePasswordRequest, User, UserStatusConstant } from "../model/user.model";
import router from "@/router";
export class UserService {
	public userCurrent: User = new User();
	constructor() {
	}
	userExist(username: string) {
		axios.get("/api/user/username/" + username).then((response) => {
			if (response.data !== null) {
				return true;
			}
		});
		return false;
	}
	register(user: User) {

		axios.post("/api/user/add", user).then((response) => {
			toast.success("Đăng ký thành công, bạn có thể đăng nhập ngay");
			setInterval(() => {
				router.push("/login");
			}, 2000);
		}).catch((error) => {
			console.log(error)
			error.response.data.forEach((element: string) => {
				toast.error(element);
			});
		});
	}

	login(username: string, password: string) {
		if (username === null || username === undefined || username === "") {
			toast.error("Tài khoản không được để trống");
			return;
		}
		if (password === null || password === undefined || password === "") {
			toast.error("Mật khẩu không được để trống");
			return;
		}
		if (username.match(/^[a-zA-Z0-9]+$/) === null) {
			toast.error("Tài khoản không được chứa ký tự đặc biệt");
			return;
		}
		if (password.length < 6) {
			toast.error("Mật khẩu phải có ít nhất 6 ký tự");
			return;
		}
		axios.get("/api/user/username/" + username).then((response) => {
			if (response.data === null) {
				toast.error("Tài khoản không tồn tại");
				return;
			}
			if (response.data.status === UserStatusConstant.STATUS_LOCK) {
				toast.error("Tài khoản đã bị khóa");
				return;
			}
			this.userCurrent = response.data;
		});
		axios.post("/api/login", {
			username: username,
			password: password
		}).then((response) => {
			localStorage.setItem("access_token", "Bearer " + response.data.access_token);
			localStorage.setItem("user", JSON.stringify(this.userCurrent));
			toast.success("Đăng nhập thành công");
		}).catch((error) => {
			toast.error(error.response.data.message);
		})
	}
	logout() {
		localStorage.removeItem("access_token");
		toast.success("Đăng xuất thành công");
	}
	isLogin() {
		return localStorage.getItem("access_token") !== null &&
			localStorage.getItem("access_token") !== undefined &&
			localStorage.getItem("access_token") !== "" &&
			localStorage.getItem("user") !== null &&
			localStorage.getItem("user") !== undefined &&
			localStorage.getItem("user") !== "";
	}
	changePassword(request: ChangePasswordRequest) {
		if(request.oldPassword === null || request.oldPassword === undefined || request.oldPassword === ""){
			toast.error("Mật khẩu cũ không được để trống");
			return;
		}
		if(request.newPassword === null || request.newPassword === undefined || request.newPassword === ""){
			toast.error("Mật khẩu mới không được để trống");
			return;
		}
		if(request.newPassword.length < 6){
			toast.error("Mật khẩu mới phải có ít nhất 6 ký tự");
			return;
		}
		if(request.newPassword !== request.confirmPassword){
			toast.error("Mật khẩu mới và xác nhận mật khẩu không khớp");
			return;
		}
		axios.post("/api/user/changePassword",request).then((response) => {
			if(response.data !== null){
				console.log(response.data)
				if(response.data.status == 200){
					toast.success(response.data.message);
					request = new ChangePasswordRequest();
				}else{
					toast.error(response.data.message);
				}
			}
		}).catch((error) => {
			toast.error("Có lỗi xảy ra, vui lòng thử lại sau");
		});
	}
}