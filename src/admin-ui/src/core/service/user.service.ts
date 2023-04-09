import axios from "axios";
import {toast} from "vue3-toastify";
import {ChangePasswordRequest, User, UserFindRequest, UserStatusConstant} from "../model/user.model";
import router from "@/router";
import {Pageable} from "@/core/model/core.base";

export class UserService {
    public userCurrent: User = new User();
    url = "/api/user/";
    constructor() {
    }

    userExist(username: string) {
        axios.get(this.url + "username/" + username).then((response) => {
            if (response.data !== null) {
                return true;
            }
        });
        return false;
    }

    register(user: User) {
        if (user.id !== undefined && user.id !== null) {
            this.updateUser(user);
            return;
        }
        axios.post(this.url + "add", user).then((response) => {
            toast.success("Đăng ký thành công, bạn có thể đăng nhập ngay");
            setInterval(() => {
            }, 2000);
        }).catch((error) => {
            console.log(error)
            error.response.data.forEach((element: string) => {
                toast.error(element);
            });
        });
    }

    updateUser(user: User) {
        axios.put(this.url + "update/" + user.id, user).then((response) => {
            toast.success("Cập nhật thông tin thành công");
            setInterval(() => {
            }, 2000);
        }).catch((error) => {
            console.log(error)
            error.response.data.forEach((element: string) => {
                toast.error(element);
            });
        });
    }

    // @ts-ignore
    async login(username: string, password: string): Promise<boolean> {
        try {
            if (username === null || username === undefined || username === "") {
                toast.error("Tài khoản không được để trống");
                return false;
            }
            if (password === null || password === undefined || password === "") {
                toast.error("Mật khẩu không được để trống");
                return false;
            }
            if (username.match(/^[a-zA-Z0-9]+$/) === null) {
                toast.error("Tài khoản không được chứa ký tự đặc biệt");
                return false;
            }
            if (password.length < 6) {
                toast.error("Mật khẩu phải có ít nhất 6 ký tự");
                return false;
            }

            const userResponse = await axios.get(this.url + "username/" + username);
            if (userResponse.data === null) {
                toast.error("Tài khoản không tồn tại");
                return false;
            }
            if (userResponse.data.status === UserStatusConstant.STATUS_LOCK) {
                toast.error("Tài khoản đã bị khóa");
                return false;
            } else {
                this.userCurrent = userResponse.data;
            }

            const response = await axios.post("/api/login", {
                username: username,
                password: password
            });

            if (response.data !== null && response.data !== undefined) {
                localStorage.setItem("access_token", response.data.data);
                localStorage.setItem("user", JSON.stringify(this.userCurrent));
                toast.success("Đăng nhập thành công");
                router.push("/");
                return true;
            } else {
                toast.error(response.data.message);
                return false;
            }
        } catch (error) {
            console.error(error);
            return false;
        }
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
        if (request.oldPassword === null || request.oldPassword === undefined || request.oldPassword === "") {
            toast.error("Mật khẩu cũ không được để trống");
            return;
        }
        if (request.newPassword === null || request.newPassword === undefined || request.newPassword === "") {
            toast.error("Mật khẩu mới không được để trống");
            return;
        }
        if (request.newPassword.length < 6) {
            toast.error("Mật khẩu mới phải có ít nhất 6 ký tự");
            return;
        }
        if (request.newPassword !== request.confirmPassword) {
            toast.error("Mật khẩu mới và xác nhận mật khẩu không khớp");
            return;
        }
        axios.post(this.url + "changePassword", request).then((response) => {
            if (response.data !== null) {
                console.log(response.data)
                if (response.data.status == 200) {
                    toast.success(response.data.message);
                    request = new ChangePasswordRequest();
                } else {
                    toast.error(response.data.message);
                }
            }
        }).catch((error) => {
            toast.error("Có lỗi xảy ra, vui lòng thử lại sau");
        });
    }

    async getUser(page: number, size: number): Promise<Pageable<User>> {
        //return Pageable<User>;
        return await axios.get(this.url + "page/" + page + "/" + size).then((response) => {
            return response.data;
        });
        return new Pageable<User>();
    }

    async deleteUser(id: number): Promise<boolean> {
        return await axios.delete(this.url + "delete/" + id).then((response) => {
            return true;
        }).catch((error) => {
            return false;
        });
    }

    async findByUsernameOrEmail(request: UserFindRequest): Promise<Array<User>> {
        return await axios.post(this.url + "findByUsernameOrEmail", request).then((response) => {
            return response.data;
        });
        return new Array<User>();
    }
}