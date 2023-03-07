package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Override
    public User doInsert(User user) {
        return null;
    }

    @Override
    public User doUpdateById(User user, Long aLong) {
        return null;
    }

    @Override
    public User doDeleteById(Long aLong) {
        return null;
    }

    @Override
    public User findById(Long aLong) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Page<User> findAll(Integer page, Integer size) {
        return null;
    }
}
