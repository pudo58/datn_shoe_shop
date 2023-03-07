package org.datn.app.core.service;

import org.datn.app.core.entity.User;
import org.datn.app.core.service.base.ServiceCore;

public interface UserService extends ServiceCore<User,Long> {
    User findByEmail(String email);
}
