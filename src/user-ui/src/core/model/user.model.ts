export class User {
    id?: number;
    username?: string;
    password?: string;
    fullName?: string;
    email?: string;
    phoneNumber?: string;
    accessToken?: string;
    address?: string;
    avatar?: string;
    role?: string;
    status?: number;
    created?: Date;
    modified?: Date;
}

export class UserLoginRequesr {
    username?: string;
    password?: string;
}

export class UserLoginResponse {
    accessToken?: string;
}

export class UserRegisterRequest {
    username?: string;
    password?: string;
    fullName?: string;
    email?: string;
    address?: string;
}

export class UserRoleConstant {
    static ROLE_ADMIN = "ROLE_ADMIN";
    static ROLE_USER = "ROLE_USER";
    static ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
}

export class UserStatusConstant {
    static STATUS_ACTIVE = 0;
    static STATUS_LOCK = 1;
}

export class UserRequest extends User {
    rePassword?: string;
    accept = true;
}

export class ChangePasswordRequest {
    oldPassword?: string;
    newPassword?: string;
    confirmPassword?: string;
    username?: string;
}

export class UploadFileRequest {
    file?: File;
}

export class UserFindRequest {
    username?: string;
    email?: string;
}